#!/bin/sh
#
# Script to create a new imageServer database with the proper permissions. 
# The mysql root password is required as an input parameter. This script 
# must be run on the mysql host machine.
#

# Check that the database name has been passed in
if [ $# != 2 ]
then
    /usr/bin/echo "\nUsage:  newDB.sh <databaseName> <root password>"
    /usr/bin/echo "  Example: newDB.sh iru rootpass \n\n"
    exit 1
fi

mysql="mysql -uroot -p$2"

###
# Scripts to create database for IRU application
###

$mysql -e "create database $1" 
/usr/bin/echo "after create"

###
# Create admin user -
#   Has ability to edit tables for the given database
###
$mysql -e "grant all privileges on $1.* to ncidev@localhost identified by 'wali@Ys' " $1
$mysql -e "grant all privileges on $1.* to ncidev@'130.14.10%' identified by 'wali@Ys' " $1
$mysql -e "grant FILE on $1.* to ncidev@localhost identified by 'wali@Ys'" 
/usr/bin/echo "after ncidev grant"

###
# Create system user -
#   Only has table level access- no ability to create tables, etc.
###
$mysql -e "grant select, insert, update, delete on $1.* to nciuser@localhost identified by '5aLiays'" $1

$mysql -e "grant select, insert, update, delete on $1.* to nciuser@'130.14.10%' identified by '5aLiays';" $1

###########
# Create all IRU tables
##########
/usr/bin/echo "Creating all IRU tables"
$mysql $1 < createIruTables.sql

/usr/bin/echo "Done"
