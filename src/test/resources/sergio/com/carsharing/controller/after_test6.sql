USE car_sharing;
UPDATE auto_customer
SET closed_rent = NULL,
    status = "active"
WHERE id = 1;	