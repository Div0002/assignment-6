# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://git@github.com/Div0002/assignment-4.git;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"

SRCREV = "edfb6ec7840c97c5ccd7ebd7b63723d6ab7d23f1"

S = "${WORKDIR}/git/server"

inherit update-rc.d

INITSCRIPT_NAME = "aesdsocket"
INITSCRIPT_PARAMS = "defaults 99"

FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket"

TARGET_LDFLAGS += "-pthread -lrt"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/aesdsocket

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesdsocket-start-stop \
        ${D}${sysconfdir}/init.d/aesdsocket
}
