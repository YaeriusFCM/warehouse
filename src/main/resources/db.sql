-- initiation of db tables
CREATE TABLE campaign(
    id bigserial,
    name varchar(64),
    CONSTRAINT campaign_pkey PRIMARY KEY (id)
)

CREATE TABLE datasource(
    id bigserial,
    name varchar(64),
    CONSTRAINT datasource_pkey PRIMARY KEY (id)
)

CREATE TABLE daily_metrics(
    id bigserial,
    datasource_id bigint NOT NULL,
    campaign_id bigint NOT NULL,
    date date NOT NULL,
    clicks integer NOT NULL DEFAULT 0,
    impressions integer NOT NULL DEFAULT 0,
    CONSTRAINT daily_metrics_pkey PRIMARY KEY (id)
)