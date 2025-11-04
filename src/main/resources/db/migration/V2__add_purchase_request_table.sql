CREATE TABLE purchase_request
(
    id          INT PRIMARY KEY,
    companyId   INT,
    description VARCHAR,
    amount      DOUBLE PRECISION,
    issueDate   timestamp,
    status      VARCHAR
);