INSERT INTO jxx_organization_master
(COMPANY_ID, COMPANY_NAME, DEPARTMENT_ID, DEPARTMENT_NAME, IS_ACTIVE, PARENET_DEPARTMENT_ID, PARENET_DEPARTMENT_NAME)
VALUES ('JXX', '제이주식회사', 'J01001', '마케팅팀', 1, 'J01000', '경영지원본부'),
       ('JXX', '제이주식회사', 'J01000', '경영지원본부', 1, 'J00000', '제이주식회사'),
       ('JXX', '제이주식회사', 'J01010', '전략기획팀', 1, 'J01000', '경영지원본부'),
       ('JXX', '제이주식회사', 'J01020', '재무관리팀', 1, 'J01000', '경영지원본부'),
       ('JXX', '제이주식회사', 'J01030', '준법경영팀', 1, 'J01000', '경영지원본부'),
       ('JXX', '제이주식회사', 'J02000', 'IT본부', 1, 'J00000', '제이주식회사'),
       ('JXX', '제이주식회사', 'J02010', '플랫폼개발팀', 1, 'J02000', 'IT본부'),
       ('JXX', '제이주식회사', 'J02011', '결재서비스개발팀', 1, 'J02000', 'IT본부'),
       ('JXX', '제이주식회사', 'J02020', '휴가서비스개발팀', 1, 'J02000', 'IT본부'),
       ('JXX', '제이주식회사', 'J02030', '인프라관리실', 1, 'J02000', 'IT본부'),
       ('JXX', '제이주식회사', 'J02031', 'DBA팀', 1, 'J02030', '인프라관리실'),
       ('JXX', '제이주식회사', 'J02032', '클라우드서버운영팀', 1, 'J02030', '인프라관리실'),
       ('JXX', '제이주식회사', 'J02040', '통합QA팀', 1, 'J02000', 'IT본부'),
       ('JXX', '제이주식회사', 'J02041', '서비스기획팀', 1, 'J02000', 'IT본부');

INSERT INTO jxx_member_leave_master
(ENTERED_DATE, EXPERIENCE_YEARS, IS_ACTIVE, MEMBER_ID, NAME, REMAINING_LEAVE, COMPANY_ID, DEPARTMENT_ID)
VALUES ('2023-08-05', 1, 1, 'U00001', '이재헌', 15.0, 'JXX', 'J01001'),
       ('2023-01-05', 1, 1, 'U00002', '하니', 15.0, 'JXX', 'J01001'),
       ('2023-08-05', 1, 1, 'U00003', '김영희', 15.0, 'JXX', 'J01020'),
       ('2024-08-05', 0, 1, 'U00004', '박지성', 15.0, 'JXX', 'J01010'),
       ('2023-08-05', 1, 1, 'U00005', '최정민', 15.0, 'JXX', 'J01010'),
       ('2023-08-05', 1, 1, 'U00006', '이승우', 15.0, 'JXX', 'J01030'),
       ('2023-08-05', 1, 1, 'U00007', '김영철', 15.0, 'JXX', 'J01001'),
       ('2023-01-05', 1, 1, 'U00008', '박민영', 15.0, 'JXX', 'J01001'),
       ('2023-08-05', 1, 1, 'U00009', '이현수', 15.0, 'JXX', 'J01020'),
       ('2024-08-05', 0, 1, 'U00010', '홍길동', 15.0, 'JXX', 'J01010'),
       ('2023-08-05', 1, 1, 'U00011', '임채호', 15.0, 'JXX', 'J01010'),
       ('2023-08-05', 1, 1, 'U00012', '서현진', 15.0, 'JXX', 'J01030'),
       ('2023-08-05', 1, 1, 'U00013', '김태연', 15.0, 'JXX', 'J01001'),
       ('2023-01-15', 1, 1, 'U00014', '이지은', 15.0, 'JXX', 'J01001'),
       ('2023-09-25', 1, 1, 'U00015', '손정의', 15.0, 'JXX', 'J01020'),
       ('2024-10-03', 0, 1, 'U00016', '장동건', 15.0, 'JXX', 'J01010'),
       ('2023-07-10', 1, 1, 'U00017', '유인나', 15.0, 'JXX', 'J01010'),
       ('2023-08-22', 1, 1, 'U00018', '김우빈', 15.0, 'JXX', 'J01030'),
       ('2023-02-14', 1, 1, 'U00019', '김소현', 15.0, 'JXX', 'J01001'),
       ('2023-11-07', 1, 1, 'U00020', '송중기', 15.0, 'JXX', 'J01020'),
       ('2024-04-18', 0, 1, 'U00021', '문채원', 15.0, 'JXX', 'J01010'),
       ('2023-06-30', 1, 1, 'U00022', '정우성', 15.0, 'JXX', 'J01030'),
       ('2023-05-08', 1, 1, 'U00023', '이지혜', 15.0, 'JXX', 'J01010'),
       ('2023-10-12', 1, 1, 'U00024', '김우빈', 15.0, 'JXX', 'J01001'),
       ('2023-03-24', 1, 1, 'U00025', '김태희', 15.0, 'JXX', 'J01020'),
       ('2023-12-20', 1, 1, 'U00026', '손담비', 15.0, 'JXX', 'J01010'),
       ('2024-06-14', 0, 1, 'U00027', '이종석', 15.0, 'JXX', 'J01030'),
       ('2023-04-05', 1, 1, 'U00028', '이나영', 15.0, 'JXX', 'J01001'),
       ('2023-11-30', 1, 1, 'U00029', '정형돈', 15.0, 'JXX', 'J01020'),
       ('2024-02-09', 0, 1, 'U00030', '이영하', 15.0, 'JXX', 'J01010'),
       ('2023-07-17', 1, 1, 'U00031', '전지현', 15.0, 'JXX', 'J01010'),
       ('2023-10-29', 1, 1, 'U00032', '정형돈', 15.0, 'JXX', 'J01030'),
       ('2005-07-15', 19, 1, 'U00031', '김민준', 15.0, 'JXX', 'J01001'),
       ('2010-03-28', 14, 1, 'U00032', '박예은', 15.0, 'JXX', 'J01001'),
       ('2015-09-10', 9, 1, 'U00033', '이정우', 15.0, 'JXX', 'J01020'),
       ('2018-12-05', 6, 1, 'U00034', '최지후', 15.0, 'JXX', 'J01010'),
       ('2019-05-20', 5, 1, 'U00035', '김서현', 15.0, 'JXX', 'J01010'),
       ('2012-08-03', 11, 1, 'U00036', '한승민', 15.0, 'JXX', 'J01030'),
       ('2009-11-11', 14, 1, 'U00037', '박서준', 15.0, 'JXX', 'J01001'),
       ('2007-06-30', 16, 1, 'U00038', '임지우', 15.0, 'JXX', 'J01001'),
       ('2016-04-25', 8, 1, 'U00039', '황지민', 15.0, 'JXX', 'J01020'),
       ('2018-02-14', 6, 1, 'U00040', '김수현', 15.0, 'JXX', 'J01010'),
       ('2017-07-01', 7, 1, 'U00041', '정지우', 15.0, 'JXX', 'J01010'),
       ('2013-10-18', 10, 1, 'U00042', '이서영', 15.0, 'JXX', 'J01030'),
       ('2010-01-07', 13, 1, 'U00043', '박서희', 15.0, 'JXX', 'J01001'),
       ('2006-12-23', 17, 1, 'U00044', '김지민', 15.0, 'JXX', 'J01001'),
       ('2019-11-05', 4, 1, 'U00045', '최유진', 15.0, 'JXX', 'J01020'),
       ('2015-05-30', 8, 1, 'U00046', '한지우', 15.0, 'JXX', 'J01010'),
       ('2018-09-17', 5, 1, 'U00047', '이승현', 15.0, 'JXX', 'J01010'),
       ('2017-02-14', 6, 1, 'U00048', '정유진', 15.0, 'JXX', 'J01030'),
       ('2011-10-09', 12, 1, 'U00049', '김지우', 15.0, 'JXX', 'J01001'),
       ('2008-08-22', 15, 1, 'U00050', '박지민', 15.0, 'JXX', 'J01001'),
       ('2005-07-15', 19, 1, 'U00031', '김민준', 15.0, 'JXX', 'J01001'),
       ('2010-03-28', 14, 1, 'U00032', '박예은', 15.0, 'JXX', 'J01001'),
       ('2015-09-10', 9, 1, 'U00033', '이정우', 15.0, 'JXX', 'J01020'),
       ('2018-12-05', 6, 1, 'U00034', '최지후', 15.0, 'JXX', 'J01010'),
       ('2019-05-20', 5, 1, 'U00035', '김서현', 15.0, 'JXX', 'J01010'),
       ('2012-08-03', 11, 1, 'U00036', '한승민', 15.0, 'JXX', 'J01030'),
       ('2009-11-11', 14, 1, 'U00037', '박서준', 15.0, 'JXX', 'J01001'),
       ('2007-06-30', 16, 1, 'U00038', '임지우', 15.0, 'JXX', 'J01001'),
       ('2016-04-25', 8, 1, 'U00039', '황지민', 15.0, 'JXX', 'J01020'),
       ('2018-02-14', 6, 1, 'U00040', '김수현', 15.0, 'JXX', 'J01010'),
       ('2017-07-01', 7, 1, 'U00041', '정지우', 15.0, 'JXX', 'J01010'),
       ('2013-10-18', 10, 1, 'U00042', '이서영', 15.0, 'JXX', 'J01030'),
       ('2010-01-07', 13, 1, 'U00043', '박서희', 15.0, 'JXX', 'J01001'),
       ('2006-12-23', 17, 1, 'U00044', '김지민', 15.0, 'JXX', 'J01001'),
       ('2019-11-05', 4, 1, 'U00045', '최유진', 15.0, 'JXX', 'J01020'),
       ('2015-05-30', 8, 1, 'U00046', '한지우', 15.0, 'JXX', 'J01010'),
       ('2018-09-17', 5, 1, 'U00047', '이승현', 15.0, 'JXX', 'J01010'),
       ('2017-02-14', 6, 1, 'U00048', '정유진', 15.0, 'JXX', 'J01030'),
       ('2011-10-09', 12, 1, 'U00049', '김지우', 15.0, 'JXX', 'J01001'),
       ('2008-08-22', 15, 1, 'U00050', '박지민', 15.0, 'JXX', 'J01001'),
       ('2016-09-02', 7, 1, 'U00071', '김하늘', 15.0, 'JXX', 'J02000'),
       ('2017-11-15', 6, 1, 'U00072', '이은지', 15.0, 'JXX', 'J02010'),
       ('2018-05-20', 5, 1, 'U00073', '박태희', 15.0, 'JXX', 'J02011'),
       ('2019-04-01', 4, 1, 'U00074', '최윤서', 15.0, 'JXX', 'J02020'),
       ('2020-10-10', 3, 1, 'U00075', '김하윤', 15.0, 'JXX', 'J02030'),
       ('2021-08-30', 2, 1, 'U00076', '이가연', 15.0, 'JXX', 'J02030'),
       ('2022-06-25', 1, 1, 'U00077', '박하윤', 15.0, 'JXX', 'J02031'),
       ('2023-12-12', 0, 1, 'U00078', '최가은', 15.0, 'JXX', 'J02032'),
       ('2024-02-05', 0, 1, 'U00079', '김태희', 15.0, 'JXX', 'J02040'),
       ('2023-09-09', 1, 1, 'U00080', '이유림', 15.0, 'JXX', 'J02041'),
       ('2022-03-14', 1, 1, 'U00081', '김가온', 15.0, 'JXX', 'J02000'),
       ('2021-06-25', 2, 1, 'U00082', '이하은', 15.0, 'JXX', 'J02010'),
       ('2020-11-05', 3, 1, 'U00083', '박지안', 15.0, 'JXX', 'J02011'),
       ('2019-08-20', 4, 1, 'U00084', '최하윤', 15.0, 'JXX', 'J02020'),
       ('2018-05-10', 5, 1, 'U00085', '김수아', 15.0, 'JXX', 'J02030'),
       ('2017-04-15', 6, 1, 'U00086', '이가온', 15.0, 'JXX', 'J02030'),
       ('2016-09-28', 7, 1, 'U00087', '박다온', 15.0, 'JXX', 'J02031'),
       ('2015-02-14', 8, 1, 'U00088', '최서아', 15.0, 'JXX', 'J02032'),
       ('2014-07-01', 9, 1, 'U00089', '김하은', 15.0, 'JXX', 'J02040'),
       ('2013-12-20', 10, 1, 'U00090', '이가윤', 15.0, 'JXX', 'J02041'),
       ('2012-05-05', 11, 1, 'U00091', '박하윤', 15.0, 'JXX', 'J02000'),
       ('2011-10-18', 12, 1, 'U00092', '최가온', 15.0, 'JXX', 'J02010'),
       ('2010-09-01', 13, 1, 'U00093', '김지안', 15.0, 'JXX', 'J02011'),
       ('2009-04-30', 14, 1, 'U00094', '이하은', 15.0, 'JXX', 'J02020'),
       ('2008-02-14', 15, 1, 'U00095', '박지안', 15.0, 'JXX', 'J02030'),
       ('2022-08-15', 2, 1, 'U00096', '김지우', 15.0, 'JXX', 'J02000'),
       ('2023-04-25', 1, 1, 'U00097', '이서준', 15.0, 'JXX', 'J02010'),
       ('2024-01-10', 0, 1, 'U00098', '박민준', 15.0, 'JXX', 'J02011'),
       ('2023-10-05', 1, 1, 'U00099', '최지우', 15.0, 'JXX', 'J02020'),
       ('2022-12-20', 2, 1, 'U00100', '김하윤', 15.0, 'JXX', 'J02030'),
       ('2023-07-30', 1, 1, 'U00101', '이수아', 15.0, 'JXX', 'J02030'),
       ('2024-03-15', 0, 1, 'U00102', '박지우', 15.0, 'JXX', 'J02031'),
       ('2022-09-28', 2, 1, 'U00103', '최하윤', 15.0, 'JXX', 'J02032'),
       ('2023-06-12', 1, 1, 'U00104', '김하은', 15.0, 'JXX', 'J02040'),
       ('2024-02-05', 0, 1, 'U00105', '이하윤', 15.0, 'JXX', 'J02041'),
       ('2022-11-18', 2, 1, 'U00106', '박하은', 15.0, 'JXX', 'J02000'),
       ('2023-08-01', 1, 1, 'U00107', '최가온', 15.0, 'JXX', 'J02010'),
       ('2024-04-10', 0, 1, 'U00108', '김민준', 15.0, 'JXX', 'J02011'),
       ('2023-10-25', 1, 1, 'U00109', '이가연', 15.0, 'JXX', 'J02020'),
       ('2022-12-01', 2, 1, 'U00110', '박가윤', 15.0, 'JXX', 'J02030'),
       ('2023-07-05', 1, 1, 'U00111', '최서아', 15.0, 'JXX', 'J02030'),
       ('2024-05-20', 0, 1, 'U00112', '김하린', 15.0, 'JXX', 'J02031'),
       ('2022-11-02', 2, 1, 'U00113', '이다은', 15.0, 'JXX', 'J02032'),
       ('2023-09-15', 1, 1, 'U00114', '박지우', 15.0, 'JXX', 'J02040'),
       ('2024-01-08', 0, 1, 'U00115', '최서윤', 15.0, 'JXX', 'J02041'),
       ('2023-02-14', 1, 1, 'U00116', '소희', 15.0, 'JXX', 'J02000'),
       ('2022-09-05', 2, 1, 'U00117', '태양', 15.0, 'JXX', 'J02010'),
       ('2024-06-20', 0, 1, 'U00118', '하늘', 15.0, 'JXX', 'J02011'),
       ('2023-11-25', 1, 1, 'U00119', '성우', 15.0, 'JXX', 'J02020'),
       ('2022-07-10', 2, 1, 'U00120', '은영', 15.0, 'JXX', 'J02030'),
       ('2023-04-30', 1, 1, 'U00121', '현주', 15.0, 'JXX', 'J02030'),
       ('2024-03-15', 0, 1, 'U00122', '현진', 15.0, 'JXX', 'J02031'),
       ('2022-08-28', 2, 1, 'U00123', '민재', 15.0, 'JXX', 'J02032'),
       ('2023-06-12', 1, 1, 'U00124', '준서', 15.0, 'JXX', 'J02040'),
       ('2024-02-05', 0, 1, 'U00125', '민지', 15.0, 'JXX', 'J02041'),
       ('2022-11-18', 2, 1, 'U00126', '서영', 15.0, 'JXX', 'J02000'),
       ('2023-08-01', 1, 1, 'U00127', '예은', 15.0, 'JXX', 'J02010'),
       ('2024-04-10', 0, 1, 'U00128', '지원', 15.0, 'JXX', 'J02011'),
       ('2023-10-25', 1, 1, 'U00129', '정우', 15.0, 'JXX', 'J02020'),
       ('2022-12-01', 2, 1, 'U00130', '도윤', 15.0, 'JXX', 'J02030');

