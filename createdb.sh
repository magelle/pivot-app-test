#!/bin/bash

docker run --name pivot-test-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres:latest