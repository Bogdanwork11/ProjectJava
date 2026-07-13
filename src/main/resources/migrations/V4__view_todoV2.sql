CREATE VIEW TODO_VIEW AS
SELECT
    id,
    text,
    author_id,
    status_id,
    created_at,
    updated_at,
    is_visible
FROM TODO_ENTITY;