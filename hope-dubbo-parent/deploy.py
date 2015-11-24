from os import *
from os.path import join

__author__ = 'hope6537'

#system('mvn clean install -Dmaven.test.skip')
list = []

def find(name):
    for root, dirs, files in walk('./'):
        if name in dirs or name in files:
            list.append(join(root, name))


find('hope-dubbo-service-1.2.1-assembly.tar.gz')
find('hope-dubbo-client-1.2.1-assembly.tar.gz')

if(isdir('~/Document/dubbo')){

}
for file in list:
    system('cp ' + file + ' ~/dubbo/')
