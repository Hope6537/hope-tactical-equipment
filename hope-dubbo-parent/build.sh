#!/usr/bin/env bash
set -e;

# Get the fully qualified path to the script
case $0 in
    /*)
        SCRIPT="$0"
        ;;
    *)
        PWD=`pwd`
        SCRIPT="$PWD/$0"
        ;;
esac
# Resolve the true real path without any sym links.
CHANGED=true
while [ "X$CHANGED" != "X" ]
do
    # Change spaces to ":" so the tokens can be parsed.
    SAFESCRIPT=`echo $SCRIPT | sed -e 's; ;:;g'`
    # Get the real path to this script, resolving any symbolic links
    TOKENS=`echo $SAFESCRIPT | sed -e 's;/; ;g'`
    REALPATH=
    for C in $TOKENS; do
        # Change any ":" in the token back to a space.
        C=`echo $C | sed -e 's;:; ;g'`
        REALPATH="$REALPATH/$C"
        # If REALPATH is a sym link, resolve it.  Loop for nested links.
        while [ -h "$REALPATH" ] ; do
            LS="`ls -ld "$REALPATH"`"
            LINK="`expr "$LS" : '.*-> \(.*\)$'`"
            if expr "$LINK" : '/.*' > /dev/null; then
                # LINK is absolute.
                REALPATH="$LINK"
            else
                # LINK is relative.
                REALPATH="`dirname "$REALPATH"`""/$LINK"
            fi
        done
    done

    if [ "$REALPATH" = "$SCRIPT" ]
    then
        CHANGED=""
    else
        SCRIPT="$REALPATH"
    fi
done
# Change the current directory to the location of the script
REALDIR=$(dirname "${REALPATH}");
cd ${REALDIR};

if [ ! -f ${REALDIR}/deploy/pom.xml ];then
    echo "[error] please run :";
    echo "sh ctl.sh -s xxxx-service -a build";
    exit 1;
fi

POMXML=$REALDIR/pom.xml;
SERVER=$(cat $POMXML | grep -A 3 'com.mogujie.service' | grep 'artifactId' |  cut -d '<' -f2 | cut -d '>' -f2);
if [ -z ${SERVER} ];then
        echo "get server version fail";
fi
TARPATH="${REALDIR}/target/${SERVER}.tar.gz";
#echo "[build] start";
mvn clean install -Dmaven.test.skip > /dev/null;
if [ ! -f ${TARPATH} ];then
        echo "[build] fail , can not find ${TARPATH}";
        exit 1;
fi

TARGET_PATH=$1

if [ ! -d ${TARPATH} ];then
        mkdir -p ${TARGET_PATH} ;
fi
cp ${TARPATH} ${TARGET_PATH}

# === install.sh====
TESLA_PATH="/usr/local/tesla"
BAK_PATH="${TESLA_PATH}/bak"
APP_PATH="${TESLA_PATH}/${SERVER}"
INSTALL_SCRIPT="${TARGET_PATH}/install.sh"
echo "#!/bin/bash" >> ${INSTALL_SCRIPT}
echo "#check target dir" >> ${INSTALL_SCRIPT}
echo "if [ ! -d ${TESLA_PATH} ];then" >> ${INSTALL_SCRIPT}
echo "  mkdir -p ${TESLA_PATH} && test -d ${TESLA_PATH} && chmod 777 ${TESLA_PATH} " >> ${INSTALL_SCRIPT}
echo "fi" >> ${INSTALL_SCRIPT}
echo "mkdir -p ${BAK_PATH}" >> ${INSTALL_SCRIPT}
echo "if [ -d ${APP_PATH} ];then" >> ${INSTALL_SCRIPT}
echo "  mv ${APP_PATH} ${BAK_PATH}" >> ${INSTALL_SCRIPT}
echo "  mv ${BAK_PATH}/${SERVER} ${BAK_PATH}/${SERVER}_\`date +%Y%m%d%H%M%S\`" >> ${INSTALL_SCRIPT}
echo "fi" >> ${INSTALL_SCRIPT}
echo "tar zxf \`dirname \$0\`/${SERVER}.tar.gz -C ${TESLA_PATH}" >> ${INSTALL_SCRIPT}
echo "if [ ! -d ${APP_PATH} ];then" >> ${INSTALL_SCRIPT}
echo "  exit 1;" >> ${INSTALL_SCRIPT}
echo "fi" >> ${INSTALL_SCRIPT}
echo "exit 0;" >> ${INSTALL_SCRIPT}
chmod +x ${INSTALL_SCRIPT}

# === start.sh ===
START_SCRIPT="${TARGET_PATH}/start.sh"
echo "#!/bin/bash" >> ${START_SCRIPT}
echo "which java > /dev/null" >> ${START_SCRIPT}
echo "if [ ! $? -eq 0 ];then" >> ${START_SCRIPT}
echo "  exit 1;" >> ${START_SCRIPT}
echo "fi" >> ${START_SCRIPT}
echo "sh ${APP_PATH}/bin/start.sh" >> ${START_SCRIPT}
#cat "if [ ! $? -eq 0 ] "
echo "exit 0;" >> ${START_SCRIPT}
chmod +x ${START_SCRIPT}

# === stop.sh ===
STOP_SCRIPT="${TARGET_PATH}/stop.sh"
echo "#!/bin/bash" >> ${STOP_SCRIPT}
echo "sh ${APP_PATH}/bin/stop.sh" >> ${STOP_SCRIPT}
echo "exit 0;" >> ${STOP_SCRIPT}
chmod +x ${STOP_SCRIPT}

# === up.sh ===
UP_SCRIPT="${TARGET_PATH}/up.sh"
echo "#!/bin/bash" >> ${UP_SCRIPT}
echo "exit 0;" >> ${UP_SCRIPT}
chmod +x ${UP_SCRIPT}
#=== down.sh ==
DOWN_SCRIPT="${TARGET_PATH}/down.sh"
echo "#!/bin/bash" >> ${DOWN_SCRIPT}
echo "exit 0;" >> ${DOWN_SCRIPT}
chmod +x ${DOWN_SCRIPT}

exit 0;