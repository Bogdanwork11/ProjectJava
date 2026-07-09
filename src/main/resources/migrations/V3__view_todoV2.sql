CREATE VIEW TODO_VIEW AS
SELECT
    id,
    text,
    status,
    created_at,
    updated_at,
    is_visible,
    author
FROM TODO_ENTITY