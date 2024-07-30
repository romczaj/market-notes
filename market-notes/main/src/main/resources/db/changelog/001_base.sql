CREATE TABLE stock_company
(
    id                   bigint       NOT NULL,
    actual_price float8,
    company_name         varchar(255) NOT NULL,
    data_provider_symbol varchar(255) NOT NULL,
    external_id          varchar(255) NOT NULL,
    stock_market_symbol  varchar(255) NOT NULL,
    stock_symbol         varchar(255) NOT NULL,
    CONSTRAINT stock_company_pkey PRIMARY KEY (id),
    CONSTRAINT unique_external_id_in_stock_company UNIQUE (external_id)
);

CREATE TABLE stock_company_calculation_result_history
(
    id                 bigint NOT NULL,
    calculation_date   timestamp(6) NULL,
    calculation_result jsonb NULL,
    stock_company_id   bigint NULL,
    CONSTRAINT stock_company_calculation_result_history_pkey PRIMARY KEY (id)
);

create view stock_company_summary_view as

WITH LatestResults AS (SELECT MAX(id) AS id
                       FROM stock_company_calculation_result_history
                       GROUP BY stock_company_id)
SELECT sc.id,
       actual_price,
       company_name,
       data_provider_symbol,
       external_id,
       stock_market_symbol,
       stock_symbol,
       s.calculation_date,
       s.calculation_result
FROM stock_company_calculation_result_history s
         JOIN LatestResults lr ON s.id = lr.id
         JOIN public.stock_company sc on sc.id = s.stock_company_id;

CREATE TABLE stock_company_note
(
    id               bigint NOT NULL,
    note_content     varchar(255) NULL,
    note_date        timestamptz(6) NULL,
    price            float8 NULL,
    price_date       date NULL,
    stock_company_id bigint NULL,
    CONSTRAINT stock_company_note_pkey PRIMARY KEY (id)
);

CREATE TABLE user_account
(
    id          bigint NOT NULL,
    email       varchar(255) NULL,
    external_id uuid NULL,
    username    varchar(255) NULL,
    CONSTRAINT unique_external_id_in_user_account UNIQUE (external_id),
    CONSTRAINT user_account_pkey PRIMARY KEY (id)
);

CREATE TABLE user_account_balance_history
(
    id              bigint NOT NULL,
    amount          float8 NULL,
    balance_date    date NULL,
    currency        varchar(255) NULL,
    user_account_id bigint NULL,
    CONSTRAINT user_account_balance_history_pkey PRIMARY KEY (id)
);

CREATE TABLE user_account_buy_sell_history
(
    id                        bigint NOT NULL,
    operation_amount          float8 NULL,
    operation_currency        varchar(255) NULL,
    operation_date            date NULL,
    stock_company_external_id varchar(255) NULL,
    stock_operation           varchar(255) NULL,
    user_account_id           bigint NULL,
    CONSTRAINT user_account_buy_sell_history_pkey PRIMARY KEY (id)
);

CREATE TABLE user_account_company_invest_goal
(
    id                        BIGINT NOT NULL,
    user_account_id           BIGINT,
    stock_company_external_id VARCHAR(255),
    buy_stop_price            float8,
    sell_stop_price           float8,
    buy_limit_price           float8,
    sell_limit_price          float8,
    CONSTRAINT user_account_company_invest_goal_pkey PRIMARY KEY (id)
);

CREATE TABLE user_account_invest_report
(
    id                         bigint NOT NULL,
    company_user_notifications jsonb NULL,
    email_message              text NULL,
    email_subject              varchar(255) NULL,
    send_date                  timestamp(6) NULL,
    success_send               bool NOT NULL,
    user_account_id            bigint NULL,
    CONSTRAINT user_account_invest_report_pkey PRIMARY KEY (id)
);

CREATE TABLE user_account_recharge_history
(
    id              bigint NOT NULL,
    amount          float8 NULL,
    currency        varchar(255) NULL,
    recharge_date   date NULL,
    user_account_id bigint NULL,
    CONSTRAINT user_account_recharge_history_pkey PRIMARY KEY (id)
);

CREATE TABLE user_account_company_comment
(
    id                        BIGINT NOT NULL,
    user_account_id           BIGINT,
    stock_company_external_id VARCHAR(255),
    note_date                 TIMESTAMP WITHOUT TIME ZONE,
    company_price             float8,
    note_content              VARCHAR(255),
    CONSTRAINT user_account_company_comment_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE user_account_company_comment_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE stock_company_calculation_result_history_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE stock_company_note_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE stock_company_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE user_account_balance_history_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE user_account_buy_sell_history_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE user_account_company_invest_goal_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE user_account_invest_report_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE user_account_recharge_history_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;

CREATE SEQUENCE user_account_seq
    INCREMENT BY 50
    MINVALUE 1
    START 1;
