DROP DATABASE IF EXISTS Rental_Car;

CREATE DATABASE Rental_Car;

USE Rental_Car;


/* =========================================================
   1. USERS - chứa chi tiết thông tin người dùng 
   ========================================================= */

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME NULL
) ;


/* =========================================================
   2. ROLES - vai trò của người dùng ( Admin , Owner , Customer)
   ========================================================= */

CREATE TABLE Roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
) ;


/* =========================================================
   3. USER_ROLES
   ========================================================= */

CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT FK_user_roles_users
        FOREIGN KEY (user_id)
        REFERENCES Users(id),

    CONSTRAINT FK_user_roles_roles
        FOREIGN KEY (role_id)
        REFERENCES Roles(role_id)
);


/* =========================================================
   4. RENTAL TYPE - 3 loại thuê xe ( nội tỉnh , liên tỉnh , một chiều ) và giá của từng loại 
   ========================================================= */

CREATE TABLE Rental_Type (
    rental_type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL,
    addition_fee DECIMAL(18,2) NOT NULL DEFAULT 0
);


/* =========================================================
   5. CARS 
   ========================================================= */

CREATE TABLE Cars (
    id INT AUTO_INCREMENT PRIMARY KEY,

    owner_id INT NOT NULL,
    car_name VARCHAR(100) NOT NULL,
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    brand VARCHAR(50),
    model VARCHAR(50),
    seat INT,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

    price_per_day DECIMAL(18,2) NOT NULL,
    fuel_type VARCHAR(30),
    description VARCHAR(1000),
    transmission VARCHAR(30),

    reservation_fee DECIMAL(18,2) NOT NULL DEFAULT 0,
    deposit_amount DECIMAL(18,2) NOT NULL DEFAULT 0,

    CONSTRAINT FK_Cars_Owner
        FOREIGN KEY (owner_id)
        REFERENCES Users(id),

    CONSTRAINT CK_Cars_Price
        CHECK (price_per_day >= 0),

    CONSTRAINT CK_Cars_Seat
        CHECK (seat > 0)
);


/* =========================================================
   6. CAR IMAGE
   ========================================================= */


CREATE TABLE Car_image (
    img_id INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT NOT NULL,
    img_url VARCHAR(500) NOT NULL,
    img_type VARCHAR(50),
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT FK_CarImage_Car
        FOREIGN KEY (car_id)
        REFERENCES Cars(id)
        ON DELETE CASCADE
);


/* =========================================================
   7. CAR RENTAL TYPE 
   ========================================================= */


CREATE TABLE Car_Rental_Type (
    car_id INT NOT NULL,
    rental_type_id INT NOT NULL,

    PRIMARY KEY (car_id, rental_type_id),

    CONSTRAINT FK_CarRentalType_Car
        FOREIGN KEY (car_id)
        REFERENCES Cars(id)
        ON DELETE CASCADE,

    CONSTRAINT FK_CarRentalType_RentalType
        FOREIGN KEY (rental_type_id)
        REFERENCES Rental_Type(rental_type_id)
);

/* =========================================================
   8.Location  - bao gồm vị trí cụ thể cho phép chủ xe và khách có thể nhận và trả xe 
   ========================================================= */


   CREATE TABLE Location (
    location_id INT AUTO_INCREMENT PRIMARY KEY,

    location_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,

    province VARCHAR(100),
    district VARCHAR(100),

    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),

    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
);



/* =========================================================
   9. BOOKING - bao gồm loại thuê xe , phí giữ chỗ , tiền đặt cọc , tổng tiền , thời gian thuê , trạng thái của xe ( pending , approval , cancelled) , địa điểm thuê 
   , thời gian thuê
   ========================================================= */

CREATE TABLE Booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_code VARCHAR(30) NOT NULL UNIQUE,

    customer_id INT NOT NULL,
    car_id INT NOT NULL,
    rental_type_id INT NOT NULL,
    
    pickup_location_id INT NOT NULL,
    return_location_id INT NOT NULL,

    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME NOT NULL,
    reservation_fee DECIMAL(18,2) NOT NULL DEFAULT 0,
    deposit_fee DECIMAL(18,2) NOT NULL DEFAULT 0,
    rental_price_per_day DECIMAL(18,2) NOT NULL,
    total_rental_amount DECIMAL(18,2) NOT NULL DEFAULT 0,

    booking_status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

    create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME NULL,

    CONSTRAINT FK_Booking_Customer
        FOREIGN KEY (customer_id)
        REFERENCES Users(id),

    CONSTRAINT FK_Booking_Car
        FOREIGN KEY (car_id)
        REFERENCES Cars(id),

    CONSTRAINT FK_Booking_RentalType
        FOREIGN KEY (rental_type_id)
        REFERENCES Rental_Type(rental_type_id) ,
        
   CONSTRAINT FK_Booking_PickupLocation
        FOREIGN KEY (pickup_location_id)
        REFERENCES Location(location_id),

   CONSTRAINT FK_Booking_ReturnLocation
        FOREIGN KEY (return_location_id)
        REFERENCES Location(location_id)
);
/* =========================================================
   11. PAYMENT - bao gồm trạng thái thanh toán (Pending , Complete, Fail ) , tổng tiền đã thanh toán ( paid_amount) , phương thức thanh toán , số tiền cần hoàn lại 
   sau khi khách trả xe 
   ========================================================= */

CREATE TABLE Payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,

    booking_id INT NOT NULL,

    payment_status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

    transaction_id VARCHAR(100),

    paid_amount DECIMAL(18,2) NOT NULL DEFAULT 0,
    paid_at DATETIME NULL,

    payment_method VARCHAR(50),

    refunded_amount DECIMAL(18,2) NOT NULL DEFAULT 0,
    refunded_at DATETIME NULL,

    CONSTRAINT FK_Payment_Booking
        FOREIGN KEY (booking_id)
        REFERENCES Booking(booking_id),

    CONSTRAINT CK_Payment_PaidAmount
        CHECK (paid_amount >= 0),

    CONSTRAINT CK_Payment_RefundAmount
        CHECK (refunded_amount >= 0),

    CONSTRAINT CK_Payment_RefundNotGreater
        CHECK (refunded_amount <= paid_amount)
);


/* =========================================================
   12. CAR LOCATION - bao gồm kinh độ , vĩ độ , thời điểm ghi lại địa điểm của xe trong thời gian cho thuê
   ========================================================= */

CREATE TABLE Car_Local (
    location_id INT AUTO_INCREMENT PRIMARY KEY,

    car_id INT NOT NULL,
    booking_id INT NULL,

    latitude DECIMAL(10,7) NOT NULL,
    longitude DECIMAL(10,7) NOT NULL,

    recorded_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT FK_CarLocal_Car
        FOREIGN KEY (car_id)
        REFERENCES Cars(id),

    CONSTRAINT FK_CarLocal_Booking
        FOREIGN KEY (booking_id)
        REFERENCES Booking(booking_id)
);


/* =========================================================
   13. HANDOVER INSPECTION - bao gồm thông tin về thời điểm kiểm tra ( lúc giao xe , lúc trả xe ) , thông tin cơ bản của xe , hình ảnh xe trước và sau khi giao , phụ 
   kiện có trong xe.
   ========================================================= */

CREATE TABLE Handover_Inspection (
    inspection_id INT AUTO_INCREMENT PRIMARY KEY,

    booking_id INT NOT NULL,
    inspected_by INT NOT NULL,

    inspection_type VARCHAR(30) NOT NULL,
    mileage DECIMAL(12,2),
    fuel_level DECIMAL(5,2),

    car_additional VARCHAR(1000),
    note VARCHAR(1000),

    evidence_url VARCHAR(500),

    inspected_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT FK_HandoverInspection_Booking
        FOREIGN KEY (booking_id)
        REFERENCES Booking(booking_id),

    CONSTRAINT FK_HandoverInspection_User
        FOREIGN KEY (inspected_by)
        REFERENCES Users(id),

    CONSTRAINT CK_HandoverInspection_Fuel
        CHECK (fuel_level >= 0 AND fuel_level <= 100)
);

USE Rental_Car;

/* =========================================================
   1. ROLES
   ========================================================= */

INSERT INTO Roles (role_name) VALUES
('ADMIN'),
('OWNER'),
('CUSTOMER');


/* =========================================================
   2. USERS
   ========================================================= */

INSERT INTO Users
(username, full_name, email, phone, address, password, status)
VALUES
('admin01', 'Nguyễn Minh Anh', 'admin01@rentalcar.com',
 '0901000001', 'Cầu Giấy, Hà Nội',
 '2a10$demo_password_admin_01', 'ACTIVE'),

('owner01', 'Trần Văn Nam', 'nam.owner@gmail.com',
 '0901000002', 'Nam Từ Liêm, Hà Nội',
 '2a10$demo_password_owner_01', 'ACTIVE'),

('owner02', 'Lê Hoàng Long', 'long.owner@gmail.com',
 '0901000003', 'Hai Bà Trưng, Hà Nội',
 '2a10$demo_password_owner_02', 'ACTIVE'),

('owner03', 'Phạm Đức Huy', 'huy.owner@gmail.com',
 '0901000004', 'Thanh Xuân, Hà Nội',
 '2a10$demo_password_owner_03', 'ACTIVE'),

('customer01', 'Nguyễn Văn Minh', 'minh.customer@gmail.com',
 '0901000005', 'Đống Đa, Hà Nội',
 '2a10$demo_password_customer_01', 'ACTIVE'),

('customer02', 'Trần Thị Lan', 'lan.customer@gmail.com',
 '0901000006', 'Hoàng Mai, Hà Nội',
 '2a10$demo_password_customer_02', 'ACTIVE'),

('customer03', 'Đỗ Quang Huy', 'huy.customer@gmail.com',
 '0901000007', 'Hà Đông, Hà Nội',
 '2a10$demo_password_customer_03', 'ACTIVE'),

('customer04', 'Vũ Minh Đức', 'duc.customer@gmail.com',
 '0901000008', 'Long Biên, Hà Nội',
 '2a10$demo_password_customer_04', 'ACTIVE');


/* =========================================================
   3. USER_ROLES
   ========================================================= */

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin01 -> ADMIN

(2, 2), -- owner01 -> OWNER
(3, 2), -- owner02 -> OWNER
(4, 2), -- owner03 -> OWNER

(5, 3), -- customer01 -> CUSTOMER
(6, 3), -- customer02 -> CUSTOMER
(7, 3), -- customer03 -> CUSTOMER
(8, 3); -- customer04 -> CUSTOMER


/* =========================================================
   4. RENTAL_TYPE
   ========================================================= */

INSERT INTO Rental_Type (type_name, addition_fee) VALUES
('IN_PROVINCE', 0),
('INTER_PROVINCE', 200000),
('ONE_WAY', 300000);


/* =========================================================
   5. LOCATION
   ========================================================= */

INSERT INTO Location
(location_name, address, province, district, latitude, longitude, status)
VALUES
('FPT Tower Cầu Giấy',
 '10 Phạm Văn Bạch, Cầu Giấy, Hà Nội',
 'Hà Nội',
 'Cầu Giấy',
 21.0285000,
 105.7839000,
 'ACTIVE'),

('FPT Mỹ Đình',
 'Dương Đình Nghệ, Nam Từ Liêm, Hà Nội',
 'Hà Nội',
 'Nam Từ Liêm',
 21.0296000,
 105.7753000,
 'ACTIVE'),

('Sân bay Nội Bài',
 'Sân bay quốc tế Nội Bài, Sóc Sơn, Hà Nội',
 'Hà Nội',
 'Sóc Sơn',
 21.2187000,
 105.8048000,
 'ACTIVE'),

('Bến xe Ninh Bình',
 'Đường Lê Đại Hành, Ninh Bình',
 'Ninh Bình',
 'Ninh Bình',
 20.2574000,
 105.9744000,
 'ACTIVE'),

('Bến xe Hải Phòng',
 'Vĩnh Niệm, Lê Chân, Hải Phòng',
 'Hải Phòng',
 'Lê Chân',
 20.8449000,
 106.6881000,
 'ACTIVE'),

('Vincom Bà Triệu',
 '191 Bà Triệu, Hai Bà Trưng, Hà Nội',
 'Hà Nội',
 'Hai Bà Trưng',
 21.0117000,
 105.8483000,
 'ACTIVE');


/* =========================================================
   6. CARS
   ========================================================= */

INSERT INTO Cars
(owner_id, car_name, license_plate, brand, model, seat, status,
 price_per_day, fuel_type, description, transmission,
 reservation_fee, deposit_amount)
VALUES
(2, 'VinFast VF5', '30K-123.45', 'VinFast', 'VF5', 5, 'APPROVED',
 500000, 'ELECTRIC',
 'Xe điện 5 chỗ, phù hợp di chuyển trong thành phố.',
 'AUTOMATIC',
 100000,
 5000000),

(2, 'Toyota Vios', '30G-456.78', 'Toyota', 'Vios', 5, 'APPROVED',
 550000, 'PETROL',
 'Sedan 5 chỗ tiết kiệm nhiên liệu.',
 'AUTOMATIC',
 100000,
 5000000),

(3, 'Hyundai Accent', '30H-789.12', 'Hyundai', 'Accent', 5, 'APPROVED',
 600000, 'PETROL',
 'Xe sedan hiện đại, phù hợp gia đình.',
 'AUTOMATIC',
 120000,
 6000000),

(3, 'Kia Carnival', '30K-234.56', 'KIA', 'Carnival', 7, 'APPROVED',
 1200000, 'DIESEL',
 'Xe 7 chỗ rộng rãi, phù hợp đi đường dài.',
 'AUTOMATIC',
 200000,
 10000000),

(4, 'Mazda CX-5', '30L-567.89', 'Mazda', 'CX-5', 5, 'APPROVED',
 900000, 'PETROL',
 'SUV 5 chỗ, nội thất cao cấp.',
 'AUTOMATIC',
 150000,
 8000000);


/* =========================================================
   7. CAR_IMAGE
   ========================================================= */

INSERT INTO Car_image
(car_id, img_url, img_type, is_primary)
VALUES
(1, 'https://example.com/cars/vf5-front.jpg', 'FRONT', TRUE),
(1, 'https://example.com/cars/vf5-side.jpg', 'SIDE', FALSE),
(1, 'https://example.com/cars/vf5-interior.jpg', 'INTERIOR', FALSE),

(2, 'https://example.com/cars/vios-front.jpg', 'FRONT', TRUE),
(2, 'https://example.com/cars/vios-side.jpg', 'SIDE', FALSE),

(3, 'https://example.com/cars/accent-front.jpg', 'FRONT', TRUE),
(3, 'https://example.com/cars/accent-interior.jpg', 'INTERIOR', FALSE),

(4, 'https://example.com/cars/carnival-front.jpg', 'FRONT', TRUE),
(5, 'https://example.com/cars/cx5-front.jpg', 'FRONT', TRUE);


/* =========================================================
   8. CAR_RENTAL_TYPE
   ========================================================= */

INSERT INTO Car_Rental_Type (car_id, rental_type_id) VALUES
(1, 1),
(1, 2),
(1, 3),

(2, 1),
(2, 2),

(3, 1),
(3, 2),
(3, 3),

(4, 1),
(4, 2),
(4, 3),

(5, 1),
(5, 2);


/* =========================================================
   9. BOOKING
   =========================================================
   Quy ước:
   - total_rental_amount = tiền thuê + phí phát sinh/đặt chỗ
   - deposit_fee = tiền cọc
   - Payment.paid_amount = tổng số tiền thực tế khách thanh toán
   ========================================================= */

INSERT INTO Booking
(
    booking_code,
    customer_id,
    car_id,
    rental_type_id,
    pickup_location_id,
    return_location_id,
    start_datetime,
    end_datetime,
    reservation_fee,
    deposit_fee,
    rental_price_per_day,
    total_rental_amount,
    booking_status
)
VALUES

-- Booking 1: Nội tỉnh
(
    'BK2026100001',
    5,
    1,
    1,
    1,
    1,
    '2026-10-05 08:00:00',
    '2026-10-07 18:00:00',
    100000,
    5000000,
    500000,
    1100000,
    'COMPLETED'
),

-- Booking 2: Liên tỉnh
(
    'BK2026100002',
    6,
    2,
    2,
    2,
    4,
    '2026-10-10 08:00:00',
    '2026-10-12 18:00:00',
    100000,
    5000000,
    550000,
    1950000,
    'CONFIRMED'
),

-- Booking 3: Một chiều
(
    'BK2026100003',
    7,
    3,
    3,
    1,
    5,
    '2026-10-15 07:30:00',
    '2026-10-17 17:30:00',
    120000,
    6000000,
    600000,
    1620000,
    'OWNER_APPROVED'
),

-- Booking 4: Nội tỉnh
(
    'BK2026100004',
    8,
    4,
    1,
    6,
    6,
    '2026-10-20 09:00:00',
    '2026-10-22 18:00:00',
    200000,
    10000000,
    1200000,
    3800000,
    'PENDING'
),

-- Booking 5: Hủy
(
    'BK2026100005',
    5,
    5,
    2,
    3,
    4,
    '2026-10-25 08:00:00',
    '2026-10-27 18:00:00',
    150000,
    8000000,
    900000,
    2150000,
    'CANCELLED'
);


/* =========================================================
   10. PAYMENT
   ========================================================= */

INSERT INTO Payment
(
    booking_id,
    payment_status,
    transaction_id,
    paid_amount,
    paid_at,
    payment_method,
    refunded_amount,
    refunded_at
)
VALUES

-- Booking 1: Thanh toán thành công + hoàn cọc
(
    1,
    'REFUNDED',
    'VNP2026100001',
    6100000,
    '2026-10-04 15:20:00',
    'VNPAY',
    5000000,
    '2026-10-07 19:00:00'
),

-- Booking 2: Thanh toán thành công
(
    2,
    'SUCCESS',
    'MOMO2026100002',
    6950000,
    '2026-10-09 10:10:00',
    'MOMO',
    0,
    NULL
),

-- Booking 3: Chưa thanh toán
(
    3,
    'PENDING',
    NULL,
    0,
    NULL,
    NULL,
    0,
    NULL
),

-- Booking 4: Chưa thanh toán
(
    4,
    'PENDING',
    NULL,
    0,
    NULL,
    NULL,
    0,
    NULL
),

-- Booking 5: Thanh toán rồi nhưng hoàn tiền
(
    5,
    'REFUNDED',
    'BANK2026100005',
    10150000,
    '2026-10-20 09:30:00',
    'BANK_TRANSFER',
    10150000,
    '2026-10-21 14:00:00'
);


/* =========================================================
   11. CAR_LOCATION
   ========================================================= */

INSERT INTO Car_Local
(car_id, booking_id, latitude, longitude, recorded_at)
VALUES

-- Xe 1 - Booking 1
(1, 1, 21.0285000, 105.7839000, '2026-10-05 08:00:00'),
(1, 1, 21.0150000, 105.8250000, '2026-10-05 10:00:00'),
(1, 1, 20.9800000, 105.8400000, '2026-10-05 12:00:00'),
(1, 1, 21.0200000, 105.7900000, '2026-10-06 16:00:00'),

-- Xe 2 - Booking 2
(2, 2, 21.0296000, 105.7753000, '2026-10-10 08:00:00'),
(2, 2, 20.9500000, 105.9000000, '2026-10-10 11:00:00'),
(2, 2, 20.8500000, 106.0000000, '2026-10-10 14:00:00'),
(2, 2, 20.2574000, 105.9744000, '2026-10-11 09:00:00'),

-- Xe 3 - Booking 3
(3, 3, 21.0285000, 105.7839000, '2026-10-15 07:30:00'),
(3, 3, 20.9500000, 106.1000000, '2026-10-15 12:00:00'),
(3, 3, 20.8449000, 106.6881000, '2026-10-16 15:00:00');


/* =========================================================
   12. HANDOVER_INSPECTION
   ========================================================= */

INSERT INTO Handover_Inspection
(
    booking_id,
    inspected_by,
    inspection_type,
    mileage,
    fuel_level,
    car_additional,
    note,
    evidence_url
)
VALUES

-- Booking 1 - Pickup
(
    1,
    2,
    'PICKUP',
    25430.00,
    85.00,
    '2 chìa khóa, lốp dự phòng, kích xe, bộ sơ cứu',
    'Xe sạch, ngoại thất bình thường, khách đã xác nhận tình trạng xe.',
    'https://example.com/evidence/bk1-pickup.jpg'
),

-- Booking 1 - Return
(
    1,
    2,
    'RETURN',
    25780.00,
    50.00,
    '2 chìa khóa, lốp dự phòng, kích xe, bộ sơ cứu',
    'Xe trả đúng hạn, không phát hiện hư hỏng mới.',
    'https://example.com/evidence/bk1-return.jpg'
),

-- Booking 2 - Pickup
(
    2,
    2,
    'PICKUP',
    45200.00,
    90.00,
    '2 chìa khóa, camera hành trình, lốp dự phòng',
    'Ngoại thất tốt.',
    'https://example.com/evidence/bk2-pickup.jpg'
),

-- Booking 2 - Return
(
    2,
    2,
    'RETURN',
    45880.00,
    65.00,
    '2 chìa khóa, camera hành trình, lốp dự phòng',
    'Không có hư hỏng mới.',
    'https://example.com/evidence/bk2-return.jpg'
),

-- Booking 3 - Pickup
(
    3,
    3,
    'PICKUP',
    18500.00,
    80.00,
    '2 chìa khóa, lốp dự phòng, kích xe',
    'Khách đã kiểm tra xe trước khi nhận.',
    'https://example.com/evidence/bk3-pickup.jpg'
);

