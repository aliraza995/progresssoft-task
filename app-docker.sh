#!/bin/bash

IMG_NAME=img-progsoft-app
CT_NAME=ct-progsoft-app
NET_NAME=progsoftnet

echo stopping $CT_NAME
docker stop $CT_NAME

echo removing $CT_NAME
docker rm -vf $CT_NAME

echo removing $IMG_NAME
docker rmi $IMG_NAME

echo updating docker contents
rm -rf docker/app/*.war
cp target/*.war docker/app

echo building $IMG_NAME
docker build -t $IMG_NAME docker/app

echo creating newtork
docker network create $NET_NAME

echo running $CT_NAME
docker run -d -p 8080:8080 --net=$NET_NAME --name $CT_NAME $IMG_NAME
