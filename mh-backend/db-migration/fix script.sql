-- SQL script to fix expense_type_id column in expense_claim_entry table

-- 1. Check for any expense_claim_entry rows with null or invalid expense_type_id
SELECT e.id, e.expense_type_id
FROM expense_claim_entry e
LEFT JOIN expense_type et ON e.expense_type_id = et.id
WHERE et.id IS NULL;

-- 2. If invalid or null expense_type_id found, update or delete as needed
-- Example: Set to a default expense_type_id (replace 1 with valid id)
-- UPDATE expense_claim_entry SET expense_type_id = 1 WHERE expense_type_id IS NULL OR expense_type_id NOT IN (SELECT id FROM expense_type);

-- 3. Drop and recreate foreign key constraint if needed
-- ALTER TABLE expense_claim_entry DROP CONSTRAINT IF EXISTS fk_expense_type;
-- ALTER TABLE expense_claim_entry ADD CONSTRAINT fk_expense_type FOREIGN KEY (expense_type_id) REFERENCES expense_type(id);

-- 4. Optional: Add NOT NULL constraint if all rows have valid expense_type_id
-- ALTER TABLE expense_claim_entry ALTER COLUMN expense_type_id SET NOT NULL;
