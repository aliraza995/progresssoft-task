angular.module('ps.controllers').controller('UploadController', function($scope, $stateParams, $base64, $state, $http ) {
	$scope.file = null;
	$scope.contentType = null;
	$scope.success = null;
	$scope.dealFile = null;
	$scope.error = null;
	$scope.getContent = function(file) {
		$scope.dealFile = file;
	}

	$scope.import = function() {
        var fd = new FormData();
        fd.append('file', $scope.dealFile);
        
        var file = {
        	"fileName" : $scope.file,
        	"contentType" : $scope.contentType,
        	"content" : $scope.dealFile
        }
        
		$http({
	    	method:'POST',
		    url : 'rest/dataimport/upload',
	    	data: file,
		    headers: { 'content-type': 'application/json' }
		}).success(function(res) { 
		   	$scope.success = "File upload was successful";
		   	$scope.error = null;
		}).error(function(err) {
			console.log(err);
		   	$scope.error = err.error;
			$scope.success = null;
		});
	}

}).controller('UploadViewController', function($scope, $stateParams,$q, $sce,$anchorScroll,$location,$timeout,$filter,
											   resultObject,Result) {
//	$scope.result = resultObject.getProperty();
//	$scope.data = {};
//	$scope.data.showCompleteXml = false;
//	$scope.xmlTransformed = false;
//	if(angular.isUndefined($scope.result.multipleResults))
//		$scope.result.multipleResults = [$scope.result];
//
//	$scope.setRuleGroupProperties = function (errors, clinicalData, type, severityToShow) {
//		$scope.ruleGroupErrors = errors;
//		$scope.payloadAvailable = clinicalData.payloadAvailable;
//		$scope.clinicalDataId = clinicalData.id;
//		$scope.ruleGroupType = type;
//		$scope.selectedSeverity = severityToShow.substring(1).toUpperCase();
//		$scope.severityFilter = {};
//		$scope.categoryFilter = {};
//		$scope.severityFilter[$scope.selectedSeverity] = true;
//		if (type == 'XSD') {
//			angular.forEach($scope.ruleGroupErrors, function (error) {
//				angular.forEach(error.errorCallStack, function (errorCallStack) {
//					angular.forEach(errorCallStack.errorLocations, function (value, option) {
//						errorCallStack.errorLocations[option] = JSON.parse(value.errorLocation);
//					});
//				});
//			});
//		}
//		var severityClicked = false;
//		$('#errors').on('shown.bs.modal', function(){
//			if (!severityClicked) {
//				$(severityToShow + "").click();
//				severityClicked = true;
//			}
//		});
//
//		$scope.filterBySeverity = function (error) {
//			return $scope.severityFilter[error.severity] || noFilter($scope.severityFilter);
//		}
//
//		$scope.filterByCategory = function (error) {
//			return $scope.categoryFilter[error.category] || noFilter($scope.categoryFilter);
//		}
//
//		$scope.getCountBySeverity = function (severity) {
//			return $filter('filter')($scope.ruleGroupErrors, { severity: severity  }).length;
//		}
//
//		$scope.getSeverities = function () {
//			return ["CRITICAL", "ERROR", "WARNING", "INFO"];
//		}
//
//		$scope.getCategories = function () {
//			return ["SYNTAX", "COMPLETENESS", "INTEROP", "VALIDITY"];
//		}
//
//		$scope.getCountByCategory = function (category) {
//			return $filter('filter')($scope.ruleGroupErrors, { category: category  }).length;
//		}
//
//		if ($scope.getCountByCategory("SYNTAX") > 0) {
//			$scope.categoryFilter["SYNTAX"] = true;
//		}
//		if ($scope.getCountByCategory("COMPLETENESS") > 0) {
//			$scope.categoryFilter["COMPLETENESS"] = true;
//		}
//		if ($scope.getCountByCategory("INTEROP") > 0) {
//			$scope.categoryFilter["INTEROP"] = true;
//		}
//		if ($scope.getCountByCategory("VALIDITY") > 0) {
//			$scope.categoryFilter["VALIDITY"] = true;
//		}
//
//		function noFilter(filterObj) {
//			return Object.
//			keys(filterObj).
//			every(function (key) { return !filterObj[key]; });
//		}
//
//	};
//
//	$scope.setRuleGroupTagProperties = function (errors, clinicalData, ruleGroupTag, severityToShow) {
//		$scope.ruleGroupErrors = [];
//
//		angular.forEach(errors, function (error) {
//			angular.forEach(error.errorCallStack, function (errorCallStack) {
//				angular.forEach(errorCallStack.ruleGroupTags, function (ruleGroupTagInError) {
//					if (ruleGroupTag.id === ruleGroupTagInError.id) {
//						$scope.ruleGroupErrors.push(error);
//					}
//				});
//			});
//		});
//
//		$scope.payloadAvailable = clinicalData.payloadAvailable;
//		$scope.clinicalDataId = clinicalData.id;
//
//		var severityClicked = false;
//		$('#errors').on('shown.bs.modal', function(){
//			if (!severityClicked) {
//				$(severityToShow + "").click();
//				severityClicked = true;
//			}
//		});
//	};
//
//	$scope.getResultLogs = function(id){
//		Result.getResultLogs({url:id},function(res){
//			$scope.resultLogs = res.resultLog;
//		});
//	}
//
//	$scope.setPayloadAndScript= function(clinicalDataId) {
//		var deferred = $q.defer();
//		Result.getClinicalData({url:clinicalDataId}, function(res) {
//			//$scope.resultLogs = res.resultLog;
//			$scope.actualPayload = res.clinicalData.originalPayload;
//			$scope.payload = res.clinicalData.originalPayload;
//			$scope.script = res.clinicalData.clinicalDataType.renderingScript;
//			deferred.resolve(res);
//		});
//		return deferred.promise;
//	}
//
//	$scope.trustUrl = function(url) {
//		return $sce.trustAsResourceUrl(url);
//	};
//
//	$scope.transformPayloadToHtml = function(script, payload) {
//		var parser = new DOMParser();
//		var xslDoc = parser.parseFromString(script, "text/xml");
//		var xmlDoc = parser.parseFromString(payload, "text/xml");
//		var xsltProcessor = new XSLTProcessor();
//		xsltProcessor.importStylesheet(xslDoc);
//
//		var div = document.createElement('div');
//		div.appendChild(xsltProcessor.transformToFragment(xmlDoc, document).cloneNode(true));
//
//		/*
//		 * The following few lines modify style that's created from xslt. The scope of default styling is too large and
//		 * it overrides the whole page rather than just the payload. The new <style/> tag scopes the styles to the
//		 * descendants of the #payload id (which contains the results pop-up window).
//		 *
//		 * In the future this could be replaced with the HTML5 attribute <style scoped>. At the moment (2-11-16) it is
//		 * only supported by Firefox.
//		 */
//		var styleNode = div.getElementsByTagName('style')[0];
//		div.removeChild(styleNode);
//		var cssToModify = styleNode.textContent;
//		styleNode.textContent = $scope.scopeCss(cssToModify);
//		div.appendChild(styleNode);
//		console.log(div);
//
//		var html = div.innerHTML;
//		$scope.html = html;
//		$scope.transformedXml = $sce.trustAsHtml(html);
//		$scope.xmlTransformed = true;
//	};
//
//	$scope.scopeCss = function(cssToModify) {
//		var noNewLines = cssToModify.replace(/(?:\r\n|\r|\n)/g, '').trim();
//		var addedScope = '#payload ' + noNewLines.replace(/}/g, '} #payload ');
//		// Removes the extra '#payload' at the end of the string
//		return addedScope.slice(0, -10);
//	};
//
//	$scope.removeTransformationXML = function(script){
//		$scope.transformedXml = $sce.trustAsHtml(script);
//		$scope.xmlTransformed = false;
//	}
//
//	$scope.transformPayloadToXML = function(){
//		$scope.payload = $scope.actualPayload;
//		$scope.xmlTransformed = false;
//	}
//
//	$scope.extractErrorLocation = function(errorLocation,clinicalDataId){
//		if (!$scope.payload) {
//			$scope.setPayloadAndScript(clinicalDataId).then(function(res) {
//				getContentByXpath(errorLocation);
//			});
//		} else {
//			getContentByXpath(errorLocation);
//		}
//	}
//
//	function getContentByXpath(errorLocation) {
//		if($scope.ruleGroupType == "Schematron") {
//			errorLocation = errorLocation.errorLocation;
//			var parser = new DOMParser();
//			var xmlDoc = parser.parseFromString($scope.payload, "text/xml");
//			var xPath
//			if(errorLocation.indexOf(":") == -1 )
//				xPath = $scope.parseLocationToXpath(errorLocation);
//			else
//				xPath = errorLocation;
//			var nsResolver = (function (element) {
//				var
//						nsResolver = element.ownerDocument.createNSResolver(element),
//						defaultNamespace = element.getAttribute('xmlns');
//				return function (prefix) {
//					return nsResolver.lookupNamespaceURI(prefix) || defaultNamespace;
//				};
//			}(xmlDoc.documentElement));
//			var errorNodes = xmlDoc.evaluate(xPath, xmlDoc, nsResolver, XPathResult.ANY_TYPE, null);
//			var result = $scope.composeXmlNodesToString(errorNodes);
//			$scope.data.errorXml = result;
//			$scope.xmlLineArray = null;
//			$scope.locationXml = $scope.data.errorXml;
//		} else {
//			$scope.xmlLineArray = null;
//			$scope.data.errorXml = $scope.payload.split(/\r\n|\r|\n/g);
//			$scope.xmlLineArray = $scope.data.errorXml;
//
//			$scope.locationLineNumber = errorLocation.line;
//			$scope.xmlLineArray.push($scope.locationLineNumber);
//			$scope.setScrollPosition();
//			//$scope.$emit('ngRepeatFinished');
//		}
//		$scope.data.completeXml = $scope.payload;
//		$scope.data.showCompleteXml = false;
//		$scope.shiftLeft = {'margin-left': '50px'};
//	}
//
//	$scope.moveErrorListToCenter = function() {
//		$scope.shiftLeft = {'margin-left': 'auto'}
//	}
//
//	$scope.parseLocationToXpath = function(location){
//		return location.toString().replace(/\//g,"/cda:");
//	}
//
//	$scope.composeXmlNodesToString = function(nodes){
//		var result = "";
//		try {
//			var thisNode = nodes.iterateNext();
//			while (thisNode) {
//				result += thisNode.outerHTML
//				thisNode = nodes.iterateNext();
//			}
//		}
//		catch (e) {
//			console.error( 'Error: Document tree modified during iteration ' + e );
//			return "";
//		}
//		return result;
//	}
//
//	$scope.showHideLocXml = function(){
//		$scope.shiftLeft={'margin-left':'50px'};
//		if($scope.data.showCompleteXml)
//			$scope.locationXml = $scope.data.completeXml;
//		else
//			$scope.locationXml = $scope.data.errorXml;
//		//$scope.locationXml = errorXml;
//	}
//
//	$scope.getElementById = function(elementId) {
//		var deferred = $q.defer(),
//		intervalKey,
//		counter = 0,
//		maxIterations = 50;
//
//		intervalKey = setInterval(function () {
//			var element = $("#" + elementId);
//			if (element.text()) {
//				deferred.resolve(element);
//				clearInterval(intervalKey);
//			} else if (counter >= maxIterations) {
//				deferred.reject("no element found");
//				clearInterval(intervalKey);
//			}
//			counter++;
//		}, 100);
//
//		return deferred.promise;
//	};
//	$scope.setScrollPosition =  function() {
//		$scope.getElementById($scope.locationLineNumber).then(function (element) {
//			$timeout(function() {			$location.hash($scope.locationLineNumber);
//			$anchorScroll();},500);
//
//		}, function (message) {
//			console.log(message);
//		});
//	};
//
//	$scope.isSeverityInList = function(ruleGroupErrors, severityType) {
//		var foundItem = $filter('filter')(ruleGroupErrors, { severity: severityType  }, true)[0];
//		var index = ruleGroupErrors.indexOf(foundItem);
//		return index >= 0;
//	};
//
//	$scope.parseToJson = function(str){
//		$scope.error.errorLocation = JSON.parse(str);
//	};
//    
//	//TODO: This (and probably many of these functions) should be moved to a location where they can be
//	//used by both the uploadController and resultController
//    $scope.printResults = function() {  
//        var printContent=document.getElementById("resultPdfView").innerHTML;
//        printWin= window.open("");
//        printWin.document.write(printContent);
//		printWin.location.reload();
//		printWin.focus();
//		printWin.print();
//		printWin.close();
//        return true;
//    }

}).directive('onReadFile', function ($parse) {
	return {
		restrict: 'A',
		scope: false,
		link: function(scope, element, attrs) {
            var fn = $parse(attrs.onReadFile);
            
			element.on('change', function(onChangeEvent) {
				var reader = new FileReader();
                
				reader.onload = function(onLoadEvent) {
					scope.$apply(function() {
						fn(scope, {$file:onLoadEvent.target.result});
					});
				};
				var file = (onChangeEvent.srcElement || onChangeEvent.target).files[0];
				reader.readAsText(file);
				console.log(file);
				scope.$apply(function() {
					scope.file = file.name;
					scope.contentType = file.type;
				});
			});
		}
	};
});
