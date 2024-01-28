/* Your scheme modifications here */
ALTER TABLE accounts
    ALTER COLUMN created_on SET DATA TYPE timestamp WITH TIME ZONE;
ALTER TABLE accounts
    ALTER COLUMN created_on SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE payments
    ALTER COLUMN created_on SET DATA TYPE timestamp WITH TIME ZONE;
ALTER TABLE payments
    ALTER COLUMN created_on SET DEFAULT CURRENT_TIMESTAMP;