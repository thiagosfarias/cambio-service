CREATE TABLE IF NOT EXISTS cambio (
    id SERIAL PRIMARY KEY,
    from_currency VARCHAR(3) NOT NULL,
    to_currency VARCHAR(3) NOT NULL,
    conversion_factor decimal(65,2) NOT NULL
)