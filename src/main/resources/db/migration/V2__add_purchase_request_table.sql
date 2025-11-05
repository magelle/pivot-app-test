CREATE TABLE purchase_request
(
    id          INT PRIMARY KEY,
    company_id   INT,
    description VARCHAR,
    amount      DOUBLE PRECISION,
    issueDate   timestamp,
    status      VARCHAR
);