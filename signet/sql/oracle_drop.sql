--
-- This is the Oracle DDL for the Signet database
-- Author Tom Poage, University of California at Davis, 10 Feb 2006
--
-- $Header: /home/hagleyj/i2mi/signet/sql/oracle_drop.sql,v 1.3 2007-03-08 07:10:11 lmcrae Exp $
--

-- These are roughly grouped by dependency order wrt foreign
-- key constraints.

-- Assignment tables
DROP TABLE signet_assignmentLimit_history;
DROP TABLE signet_assignment_history;
DROP TABLE signet_assignmentLimit;
DROP TABLE signet_assignment;
DROP TABLE signet_proxy_history;
DROP TABLE signet_proxy;

-- ChoiceSet tables
DROP TABLE signet_choice;
DROP TABLE signet_choiceSet;

-- Subsystem tables
DROP TABLE signet_permission_limit;
DROP TABLE signet_function_permission;
DROP TABLE signet_permission;
DROP TABLE signet_limit;
DROP TABLE signet_function;
DROP TABLE signet_category;
DROP TABLE signet_subsystem;

-- Tree tables
DROP TABLE signet_treeNodeRelationship;
DROP TABLE signet_treeNode;
DROP TABLE signet_tree;

-- Signet Subject table
DROP TABLE signet_subject;
DROP TABLE signet_subjectAttribute;

-- Local Source Subject tables (optional)
DROP TABLE SubjectAttribute;
DROP TABLE Subject;
DROP TABLE SubjectType;

-- Common primary key sequences for all tables
DROP SEQUENCE categorySerial;
DROP SEQUENCE functionSerial;
DROP SEQUENCE permissionSerial;
DROP SEQUENCE limitSerial;
DROP SEQUENCE subjectSerial;
DROP SEQUENCE subjectAttributeSerial;
DROP SEQUENCE choiceSetSerial;
DROP SEQUENCE choiceSerial;
DROP SEQUENCE assignmentSerial;
DROP SEQUENCE assignmentHistorySerial;
DROP SEQUENCE proxySerial;
DROP SEQUENCE proxyHistorySerial


-- If Oracle 10g, clean up recycle bin
-- PURGE RECYCLEBIN;