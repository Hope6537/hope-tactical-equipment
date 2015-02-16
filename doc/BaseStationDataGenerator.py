## Copyright (C) 2013 James.J.Huang

""" 
Base Station Data Generator
Generate simulate data from base station of mobile operator
"""

import sys
import time
import random


output = open(sys.argv[1], "w")  ## output path
users = int(sys.argv[2]);  ## number of users
date = str(sys.argv[3]);  ## date like "2013-09-12"
source = str(sys.argv[4]);  ## 'pos' or 'net'

max_station = 200;  ## Maximum station number
init_time = time.strptime(date + ' 00:00:00', '%Y-%m-%d %H:%M:%S')
init_time = int(time.mktime(init_time))  ## Initial time in UNIX format
lambd_line = 1. / 175.;  ## parameter of the exponential distribution of lines
lambd_station = 1. / 6.;  ## parameter of the exponential distribution of stations


def generate(imsi):
    """
    Generate one user's data
    """
    num_line = randLine();
    while (True):
        num_station = randStation();
        if num_station <= min(num_line, max_station): break;

    ls = stations(num_station, num_line);
    ts = timeStamps(num_line);

    data = [];
    if source == 'pos':
        for i in xrange(num_line):
            data.append(formatLinePos(imsi, ls[i], ts[i]));
    if source == 'net':
        for i in xrange(num_line):
            data.append(formatLineNet(imsi, ls[i], ts[i]));
    return data


def formatLinePos(i, l, t):
    """
    Format the data
    """
    imei = ( i + sys.maxint ) % 100000000;
    status = random.randint(0, 3);
    return "%010d\t%010d\t%s\t%08d\t%s\n" % (i, imei, status, l, t);


def formatLineNet(i, l, t):
    """
    Format the data
    """
    imei = ( i + sys.maxint ) % 100000000;
    return "%010d\t%010d\t%08d\t%s\t%s\n" % (i, imei, l, t, 'www.baidu.com');


def randStation():
    """
    Calculate the number of base stations user has communicated
    Assume the figure follow the Exponential distribution
    """
    return int(random.expovariate(lambd_station) + 1.)


def randLine():
    """
    Calculate the number of lines generate by user
    Assume the figure follow the Exponential distribution
    """
    return int(random.expovariate(lambd_line) + 1.)


def stations(s, n):
    """
    Generate a list of locations with size *n* from a station list with size *s*
    """
    candidate = set()
    while ( len(candidate) < s ):
        candidate.add(random.randint(0, max_station - 1));
    candidate = list(candidate);
    ls = [];
    for i in xrange(n):
        ls.append(candidate[random.randint(0, s - 1)]);
    return ls;


def timeStamps(n):
    """
    Generate a list of timeStamps with size *n*
    """
    times = []
    for i in xrange(n):
        times.append(random.randint(0, 86399));
    list.sort(times);
    ts = [];
    for i in xrange(n):
        t = time.localtime(init_time + times[i]);
        ts.append(time.strftime('%Y-%m-%d %H:%M:%S', t))
    return ts;


for u in xrange(users):
    output.writelines(generate(u));
output.close()

