DELETE FROM OFFENDER where OFFENDER_PK = 1234;

DELETE FROM OASYS_ANSWER where OASYS_QUESTION_PK in (SELECT OASYS_QUESTION_PK from OASYS_QUESTION where OASYS_SECTION_PK in (SELECT OASYS_SECTION_PK from OASYS_SECTION where OASYS_SET_PK = 9487347) );
DELETE FROM OASYS_ANSWER where OASYS_QUESTION_PK in (SELECT OASYS_QUESTION_PK from OASYS_QUESTION where OASYS_SECTION_PK in (SELECT OASYS_SECTION_PK from OASYS_SECTION where OASYS_SET_PK = 9487348) );

DELETE FROM OASYS_QUESTION where OASYS_SECTION_PK in (SELECT OASYS_SECTION_PK from OASYS_SECTION where OASYS_SET_PK = 9487347);
DELETE FROM OASYS_QUESTION where OASYS_SECTION_PK in (SELECT OASYS_SECTION_PK from OASYS_SECTION where OASYS_SET_PK = 9487348);

DELETE FROM OASYS_SECTION where OASYS_SET_PK in (9487347, 9487348);

DELETE FROM OASYS_SET where OASYS_SET_PK in (9487347, 9487348);

DELETE FROM OASYS_ASSESSMENT_GROUP where OASYS_ASSESSMENT_GROUP_PK = 767461;
