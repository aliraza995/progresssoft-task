package com.progresssoft.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.progresssoft.dao.DealDao;
import com.progresssoft.dao.DealPerCurrencyDao;
import com.progresssoft.dao.FileDao;
import com.progresssoft.dao.InvalidDealDao;
import com.progresssoft.dto.DealDto;
import com.progresssoft.dto.DealsPerCurrencyDto;
import com.progresssoft.dto.FileDto;
import com.progresssoft.entity.DealEntity;
import com.progresssoft.entity.DealsPerCurrencyEntity;
import com.progresssoft.entity.FileEntity;
import com.progresssoft.entity.InvalidDealEntity;
import com.progresssoft.util.DealMapper;
import com.progresssoft.util.FileMapper;

@Component
public class DataIngestionManagerImpl implements DataIngestionManager {
	
    private static final Logger LOG = LogManager.getLogger();

	@Autowired
	private FileDao fileDao;
	@Autowired
	private DealDao dealDao;
	@Autowired
	private InvalidDealDao invalidDealDao;
	@Autowired
	private DealPerCurrencyDao  dealsPerCurrencyDao;
	@Autowired
	private Validator validator;
	
	@Override
	public void importData(FileDto file) {
		try {
			FileDto resultfile = createFile(file);
			List<DealDto> deals = readCSV(resultfile);
			
			List<DealEntity> validDeals = new ArrayList<>();
			List<InvalidDealEntity> invalidDeals = new ArrayList<>();
			
			deals.parallelStream().forEach(deal -> {
				Set<ConstraintViolation<DealDto>> constraintViolations = validator.validate(deal);
				if (constraintViolations != null && !constraintViolations.isEmpty()) {
					invalidDeals.add(DealMapper.INSTANCE.toInvalidEntity(deal));
				} else {
					DealEntity entity = DealMapper.INSTANCE.toEntity(deal);
					System.out.println("Invalid:" + entity.getDealId() + "|" + entity.getOrderingCurrency() + "|"
							+ entity.getToCurrency() + "|" + entity.getDealTime() + "|" + entity.getAmount() + "|" + entity.getSource());
					validDeals.add(entity);
				}
			});
			
			dealDao.addMulti(validDeals);
			invalidDealDao.addMulti(invalidDeals);
			updateCurrencyToDealCounts(validDeals);
		} catch (IllegalStateException e) {
			//new module exception
		} catch (IOException e) {
			
		}
	}
	
	private void updateCurrencyToDealCounts(List<DealEntity> validDeals) {
		CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				Map<String, Long> currencyToDealCountMap = new HashMap<>();
				validDeals.forEach(deal -> {
					if (currencyToDealCountMap.containsKey(deal.getOrderingCurrency())) {
						long count = currencyToDealCountMap.get(deal.getOrderingCurrency()) + 1;
						currencyToDealCountMap.put(deal.getOrderingCurrency(), count);
					} else {
						currencyToDealCountMap.put(deal.getOrderingCurrency(), 1l);
					}
				});
				
				List<DealsPerCurrencyEntity> dealsPerCurrencies = new ArrayList<>();
				currencyToDealCountMap.forEach((currency, count) -> {
					DealsPerCurrencyDto entity = new DealsPerCurrencyDto();
					entity.setCurrency(currency);
					entity.setCount(count);
				});
				dealsPerCurrencyDao.addMulti(dealsPerCurrencies);
			}
		});
	}
	
	private FileDto createFile(FileDto file) throws IOException {
		FileEntity entity = new FileEntity();
		entity.setFileName(file.getFileName());
		entity.setContent(file.getContent());
		
		FileEntity saved = fileDao.add(entity);
		return FileMapper.INSTANCE.toDto(saved);
	}

	private List<DealDto> readCSV(FileDto file) {
		List<DealDto> deals = new ArrayList<>(); 
		try {
			CSVParser csvParser = new CSVParser(new StringReader(file.getContent()), CSVFormat.DEFAULT.withHeader());
			List<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord record : csvRecords) {
				DealDto deal = new DealDto();
				deal.setDealId(record.get("Deal_Id").trim());
				deal.setOrderingCurrency(record.get("Ordering_Currency").trim());
				deal.setToCurrency(record.get("To_Currency").trim());
				if (record.get("Time").trim().equals("")) {
					deal.setDealTime(null);
				} else {
					deal.setDealTime(Long.parseLong(record.get("Time").trim()));
				}
				deal.setAmount(record.get("Amount").trim( ));
				deal.setSource(file);
				deals.add(deal);
			}
			csvParser.close();
			return deals;
		} catch (IOException | NumberFormatException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException("Unable to CSV process file", e);
		}
	}

}
