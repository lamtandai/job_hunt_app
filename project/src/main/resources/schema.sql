CREATE TABLE IF NOT EXISTS memberships(
    mbs_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    mbs_name VARCHAR(20) NOT NULL,
    mbs_fee VARCHAR(10) NOT NULL,
    mbs_feature VARCHAR(255) NOT NULL,
    
    mbs_createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mbs_updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS membership_durations(
    duration_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    duration_name VARCHAR(10) NOT NULL DEFAULT 'BLANK',
    duration_value TINYINT NOT NULL DEFAULT 1
);



CREATE TABLE IF NOT EXISTS industries(
    idt_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    idt_name VARCHAR(50) NOT NULL DEFAULT 'BLANK',
    idt_activated BOOLEAN DEFAULT TRUE,
    idt_createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    idt_updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS skills(
    sk_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    sk_name VARCHAR(50) NOT NULL,
    sk_activated BOOLEAN DEFAULT TRUE,
    sk_createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sk_updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    
);
CREATE TABLE IF NOT EXISTS companies(

    cpn_id INT AUTO_INCREMENT PRIMARY KEY,
    cpn_name VARCHAR(50) NOT NULL DEFAULT 'BLANK',
    cpn_description MEDIUMTEXT NOT NULL ,
    cpn_idt_id TINYINT NOT NULL DEFAULT 1,

    cpn_number_of_follower INT NOT NULL DEFAULT 0,
    cpn_size TINYINT NOT NULL DEFAULT 0,
    cpn_logo VARCHAR(255) NOT NULL DEFAULT 'BLANK',
    cpn_location VARCHAR(255) NOT NULL DEFAULT 'BLANK',
    cpn_website_url VARCHAR(500) NOT NULL DEFAULT 'BLANK',

    cpn_verified BOOLEAN DEFAULT FALSE,
    cpn_deleted BOOLEAN DEFAULT FALSE,

    cpn_created_by BIGINT NOT NULL DEFAULT 0,
    cpn_updated_by BIGINT NOT NULL DEFAULT 0,

    cpn_createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cpn_updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (cpn_idt_id) REFERENCES industries(idt_id)
    
);

CREATE TABLE IF NOT EXISTS user_accounts(
    us_account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    us_name VARCHAR (255) NOT NULL DEFAULT 'BLANK' ,
    us_password VARCHAR (255) NOT NULL DEFAULT 'BLANK',
    us_email VARCHAR(40) NOT NULL DEFAULT 'BLANK',
    us_avatar VARCHAR(255) DEFAULT 'BLANK',
    us_gender VARCHAR(5) NOT NULL DEFAULT 'BLANK', 
    us_role VARCHAR(10) NOT NULL DEFAULT 'BLANK',
    us_location VARCHAR(100)  NOT NULL DEFAULT 'BLANK',
    us_phone VARCHAR(15) DEFAULT 'BLANK',
    us_refresh_token MEDIUMTEXT ,
    us_active BOOLEAN DEFAULT TRUE ,
    us_deleted BOOLEAN DEFAULT FALSE,
    us_cpn_id INT DEFAULT NULL, -- changed from DEFAULT 0 to DEFAULT NULL

    us_createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    us_updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (us_cpn_id) REFERENCES companies(cpn_id)


); 


CREATE TABLE IF NOT EXISTS user_logs (
    usl_user_account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usl_last_login_date TIMESTAMP,
    usl_last_job_apply_date TIMESTAMP,

    FOREIGN KEY (usl_user_account_id) REFERENCES user_accounts(us_account_id)
);

CREATE TABLE IF NOT EXISTS jobPosts(
    jp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    jp_recruiter_id BIGINT ,

    jp_title VARCHAR(255) DEFAULT 'BLANK',

    jp_description VARCHAR(255) DEFAULT 'BLANK',
    jp_salary_range VARCHAR(50) DEFAULT 'BLANK',
    
    jp_status VARCHAR(50) DEFAULT 'BLANK',
    jp_number_of_recruitment TINYINT DEFAULT 0,

    jp_posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    jp_expired_at TIMESTAMP,

    jp_created_by BIGINT,
    jp_updated_by BIGINT,

    jp_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    jp_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (jp_created_by) REFERENCES user_accounts(us_account_id),
    FOREIGN KEY (jp_updated_by) REFERENCES user_accounts(us_account_id),
    FOREIGN KEY (jp_recruiter_id) REFERENCES user_accounts(us_account_id)
   
);


CREATE TABLE IF NOT EXISTS applications(
    app_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    app_us_id BIGINT ,
    app_jp_id BIGINT,
    app_status VARCHAR(10) ,

    app_apply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 

    FOREIGN KEY (app_us_id) REFERENCES user_accounts(us_account_id),
    FOREIGN KEY (app_jp_id) REFERENCES jobPosts(jp_id)

);

CREATE TABLE IF NOT EXISTS skills_for_jobPost(
    jp_id BIGINT ,
    sk_id TINYINT ,

    PRIMARY KEY(jp_id, sk_id),
    FOREIGN KEY(jp_id) REFERENCES jobPosts(jp_id),
    FOREIGN KEY(sk_id) REFERENCES skills(sk_id)
);

CREATE TABLE IF NOT EXISTS user_memberships (
  user_id    BIGINT    ,
  membership_id    TINYINT   ,
  start_date TIMESTAMP       ,
  end_date   TIMESTAMP       ,
  PRIMARY KEY (user_id, start_date),
  FOREIGN KEY (user_id) REFERENCES user_accounts(us_account_id),
  FOREIGN KEY (membership_id) REFERENCES memberships(mbs_id)
);
