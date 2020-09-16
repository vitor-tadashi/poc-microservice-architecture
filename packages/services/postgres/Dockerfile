FROM postgres:13-alpine

ENV POSTGRES_DB postgis
ENV POSTGRES_USER developer
ENV POSTGRES_PASSWORD development

COPY ./create_schema.sql /docker-entrypoint-initdb.d/create_schema.sql