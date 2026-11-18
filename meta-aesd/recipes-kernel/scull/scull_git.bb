SUMMARY = "LDD3 SCULL character device driver"
DESCRIPTION = "SCULL kernel module for AESD Assignment 7"

LICENSE = "CLOSED"

SRC_URI = "git://github.com/Div0002/assignment-7.git;protocol=https;branch=master"
SRCREV = "11ddc6d1838079a17511c30183ff8fae717964f1"


FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://scull-devnodes"

inherit module update-rc.d

INITSCRIPT_NAME = "scull-devnodes"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."


MODULES_MODULE_SYMVERS_LOCATION = "scull"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS

    oe_runmake -C ${STAGING_KERNEL_DIR} \
        O=${STAGING_KERNEL_BUILDDIR} \
        M=${S}/scull \
        CC="${KERNEL_CC}" \
        LD="${KERNEL_LD}" \
        AR="${KERNEL_AR}" \
        OBJCOPY="${KERNEL_OBJCOPY}" \
        STRIP="${KERNEL_STRIP}" \
        KBUILD_EXTRA_SYMBOLS="${KBUILD_EXTRA_SYMBOLS}" \
        modules
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra

    install -m 0644 ${S}/scull/scull.ko \
        ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/scull-devnodes \
        ${D}${sysconfdir}/init.d/scull-devnodes
}

KERNEL_MODULE_AUTOLOAD += "scull"

FILES:${PN} += "${sysconfdir}/init.d/scull-devnodes"
