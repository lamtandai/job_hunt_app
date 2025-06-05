CREATE TABLE IF NOT EXISTS memberships(
    membership_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    membership_name VARCHAR(20) NOT NULL,
    membership_fee VARCHAR(10) NOT NULL,
    feature VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS industries(
    industry_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    industry_name VARCHAR(50),
    activated BOOLEAN DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS skills(
    skill_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    skill_name VARCHAR(50),
    activated BOOLEAN DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS companies(
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(50),
    numberFollower INT,
    size TINYINT,
    logo VARCHAR(255),
    industry_id TINYINT NOT NULL,
    description VARCHAR(255),
    location VARCHAR(255),
    website_url VARCHAR(500),
    verified BOOLEAN DEFAULT FALSE,

    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (industry_id) REFERENCES industries(industry_id)
);

CREATE TABLE IF NOT EXISTS user_accounts(
    user_account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR (255) NOT NULL,
    password VARCHAR (255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    avatar VARCHAR(255),
    user_role VARCHAR(10) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    phone VARCHAR(15),
    company_id INT, 
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (company_id)  REFERENCES companies(company_id)

); 

CREATE TABLE IF NOT EXISTS user_logs (
    user_account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    last_login_date TIMESTAMP ,
    last_job_apply_date TIMESTAMP,

    FOREIGN KEY (user_account_id) REFERENCES user_accounts(user_account_id)
);
CREATE TABLE IF NOT EXISTS jobs(
    job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_name VARCHAR(50) NOT NULL,
    industry_id TINYINT NOT NULL,
    
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (industry_id) REFERENCES industries(industry_id)
);

CREATE TABLE IF NOT EXISTS jobPosts(
    job_post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recruiter_id BIGINT NOT NULL,
    job_post_title VARCHAR(255),
    job_id BIGINT NOT NULL, 
    description VARCHAR(255),
    salary_range VARCHAR(50),
    job_status VARCHAR(50),
    number_of_recruitment TINYINT,
    posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expired_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (recruiter_id) REFERENCES user_accounts(user_account_id),
    FOREIGN KEY (job_id) REFERENCES jobs(job_id)
);



CREATE TABLE IF NOT EXISTS applications(
    application_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    job_post_id BIGINT NOT NULL,
    application_status VARCHAR(20) NOT NULL,
    apply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 

    FOREIGN KEY (user_id) REFERENCES user_accounts(user_account_id),
    FOREIGN KEY (job_post_id) REFERENCES jobPosts(job_post_id)

);

CREATE TABLE IF NOT EXISTS skills_for_jobPost(
    job_post_id BIGINT NOT NULL,
    skill_id TINYINT NOT NULL,

    PRIMARY KEY(job_post_id, skill_id),
    FOREIGN KEY(job_post_id) REFERENCES jobPosts(job_post_id),
    FOREIGN KEY(skill_id) REFERENCES skills(skill_id)
);

CREATE TABLE IF NOT EXISTS user_memberships (
  user_id    BIGINT     NOT NULL,
  membership_id    TINYINT        NOT NULL,
  start_date TIMESTAMP       ,
  end_date   TIMESTAMP       ,
  PRIMARY KEY (user_id, start_date),
  FOREIGN KEY (user_id) REFERENCES user_accounts(user_account_id),
  FOREIGN KEY (membership_id) REFERENCES memberships(membership_id)
);