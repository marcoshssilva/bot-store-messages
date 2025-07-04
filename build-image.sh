#!/bin/bash
mvn clean package
docker build --tag marcoshssilva/bot-store-messages:latest --file Dockerfile.jenkins .