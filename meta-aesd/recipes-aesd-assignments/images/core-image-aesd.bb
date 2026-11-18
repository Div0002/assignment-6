inherit core-image

CORE_IMAGE_EXTRA_INSTALL += "aesd-assignments"
CORE_IMAGE_EXTRA_INSTALL += "openssh"

inherit extrausers

EXTRA_USERS_PARAMS = "usermod -p '${PASSWD}' root;"

EXTRA_IMAGE_FEATURES:append = " allow-root-login"
IMAGE_FSTYPES:append = " ext4"

# Assignment 7: LDD3 kernel modules
IMAGE_INSTALL:append = " scull kernel-module-scull kernel-module-hello kernel-module-faulty"

# Ensure LDD3 module RPMs are available when constructing the root filesystem.
do_rootfs[depends] += "misc-modules:do_package_write_rpm"

# Install the AESD test SSH public key for root access.
ROOTFS_POSTPROCESS_COMMAND += "install_aesd_ssh_key;"

install_aesd_ssh_key() {
    install -d -m 0700 ${IMAGE_ROOTFS}/home/root/.ssh

    install -m 0600 \
        ${THISDIR}/files/aeld_id_rsa_nopassphrase.pub \
        ${IMAGE_ROOTFS}/home/root/.ssh/authorized_keys
}
