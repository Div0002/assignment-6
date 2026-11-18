SUMMARY = "LDD3 miscellaneous kernel modules"
DESCRIPTION = "Miscellaneous kernel modules for AESD Assignment 7"

LICENSE = "CLOSED"

SRC_URI = "git://github.com/Div0002/assignment-7.git;protocol=https;branch=master"
SRCREV = "11ddc6d1838079a17511c30183ff8fae717964f1"


inherit module

MODULES_MODULE_SYMVERS_LOCATION = "misc-modules"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS

    oe_runmake -C ${STAGING_KERNEL_DIR} \
        O=${STAGING_KERNEL_BUILDDIR} \
        M=${S}/misc-modules \
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

    for module in ${S}/misc-modules/*.ko; do
        install -m 0644 "$module" \
            ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    done
}

KERNEL_MODULE_AUTOLOAD += "hello faulty"
