-- INSERT INTO memberships (membership_name, membership_fee, feature) VALUES
--  ('Silver',   '.','Basic resume boosts'),
--  ('Gold',    '1.','Highlighted listing + Silver'),
--  ('Platinum','2.','Unlimited applies + Gold'),
--  ('Diamond', '.','All features + 1:1 coaching');

-- INSERT IGNORE INTO user_accounts (username, password, email, avatar, user_role) VALUES 
-- ('john_doe', 'pass12', 'john@example.com', 'default.png', 'Job_seeker'),
-- ('john_doe12', 'pass', 'king@example.com', 'default.png', 'Job_seeker'),
-- ('long_dang', 'pass22', 'queen@example.com', 'default.png', 'Job_seeker'),
-- ('micheal_jack-son', 'pass', 'squeeze@example.com', 'default.png', 'Job_seeker');


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

-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ('Java', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Python', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'SQL', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'DevOps', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Cloud Computing', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Machine Learning', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Cybersecurity', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'React', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Angular', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Node.js', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Financial Analysis', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Accounting', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Budgeting', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Excel', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Taxation', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Investment Strategies', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Auditing', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Negotiation', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'CRM', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Lead Generation', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Closing Sales', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'B2B Sales', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Cold Calling', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'SEO', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Content Marketing', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Social Media', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Email Marketing', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Market Research', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Google Ads', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ('Ad Copywriting', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ('Media Buying', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Brand Strategy', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Campaign Management', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Press Release Writing', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Crisis Communication', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES (,'Event Planning', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ('Communication', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Teamwork', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Problem Solving', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Crop Management', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Soil Science', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'AgriTech', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Customer Service', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Time Management', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Conflict Resolution', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Net Handling', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Fish Processing', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Navigation', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Pattern Making', 1, 1);;
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Sewing', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Fabric Selection', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Soldering', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'PCB Design', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Circuit Analysis', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Property Valuation', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Real Estate Law', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Negotiation', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Blueprint Reading', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Carpentry', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Concrete Work', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Food Safety', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Packaging', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Quality Control', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Grid Management', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Water Treatment', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Energy Efficiency', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Acting', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Directing', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Script Writing', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Supply Chain Management', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Inventory Control', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Shipping Coordination', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Route Planning', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Fleet Management', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Driving', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Recruitment', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Employee Relations', 1, 1);
-- INSERT INTO skills ( sk_name, sk_created_by, sk_updated_by) VALUES ( 'Payroll', 1, 1);

-- INSERT IGNORE INTO skills(skill_name) VALUES 
-- (),



-- INSERT IGNORE INTO jobPosts(posted_by_user_id, jobPost_title, description, jobPost_salary_range) VALUES
-- ()

-- INSERT IGNORE INTO applications(user_id, jobPost_id, application_status, apply_date) VALUES
-- ()

-- INSERT IGNORE INTO skills_for_jobPost(jobPost_id, skill_id) VALUES
-- ()