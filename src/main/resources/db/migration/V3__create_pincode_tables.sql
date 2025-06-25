
CREATE TABLE pincode (
    pincode_id BIGSERIAL PRIMARY KEY,
    pincode BIGINT NOT NULL,
    city_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    CONSTRAINT fk_pincode_city FOREIGN KEY (city_id) REFERENCES cities(city_id)
);


