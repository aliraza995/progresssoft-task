#!/bin/bash

IMG_NAME=img-progsoft-db
CT_NAME=ct-progsoft-db
NET_NAME=progsoftnet

echo stopping $CT_NAME
docker stop $CT_NAME

echo removing $CT_NAME
docker rm -vf $CT_NAME

echo removing $IMG_NAME
docker rmi $IMG_NAME

echo building $IMG_NAME
docker build -t $IMG_NAME docker/db

echo creating newtork
docker network create $NET_NAME

echo running $CT_NAME
docker run -d -p 3306:3306 --net-alias=db --net=$NET_NAME --name $CT_NAME $IMG_NAME
