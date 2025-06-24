-- INSERT INTO memberships (membership_name, membership_fee, feature) VALUES
--  ('Silver',   '9.99','Basic resume boosts'),
--  ('Gold',    '19.99','Highlighted listing + Silver'),
--  ('Platinum','29.99','Unlimited applies + Gold'),
--  ('Diamond', '49.99','All features + 1:1 coaching');

-- INSERT IGNORE INTO user_accounts (username, password, email, avatar, user_role) VALUES 
-- ('john_doe', 'pass123', 'john@example.com', 'default.png', 'Job_seeker'),
-- ('john_doe123', 'pass6456', 'king@example.com', 'default.png', 'Job_seeker'),
-- ('long_dang', 'pass5223', 'queen@example.com', 'default.png', 'Job_seeker'),
-- ('micheal_jack-son', 'pass756743', 'squeeze@example.com', 'default.png', 'Job_seeker');


INSERT IGNORE INTO industries(idt_name) VALUES 
('INFORMATION TECHNOLOGY'),
('FINANCE'),
('SALES'),
('MARKETING'),
('ADVERTISEMENT'),
('PUBLIC RELATIONSHIP'),
('NORMAL'),
('AGRICULTURE'),
('SERVICE'),
('FISHING'),
('TEXTILES'),
('ELECTRONIC MANUFACTURING'),
('REAL ESTATE'),
('CONSTRUCTION'),
('FOOD PROCESSING'),
('PUBLIC UTILITIES'),
('ENTERTAINMENT'),
('LOGISTIC'),
('TRANSPORTATION'),
('HUMAN RESOURCES');

-- INSERT IGNORE INTO skills(skill_name) VALUES 
-- (),



-- INSERT IGNORE INTO jobPosts(posted_by_user_id, jobPost_title, description, jobPost_salary_range) VALUES
-- ()

-- INSERT IGNORE INTO applications(user_id, jobPost_id, application_status, apply_date) VALUES
-- ()

-- INSERT IGNORE INTO skills_for_jobPost(jobPost_id, skill_id) VALUES
-- ()