SUMMARY = "RaspberryPi dvbsoftwareca for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "libdvbcsa"
RDEPENDS_${PN} = "libdvbcsa"

COMPATIBLE_MACHINE = "raspberrypi|raspberrypi0|raspberrypi2|raspberrypi3"

SRCREV = "${AUTOREV}"

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRC_URI = "git://github.com/PLi-metas/rpi-dvbsoftwareca.git;protocol=git"

S = "${WORKDIR}/git"

inherit module machine_kernel_pr gitpkgv

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_BUILDDIR}"

do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C "${STAGING_KERNEL_BUILDDIR}" M="${S}" modules
}

do_install() {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/media/dvb-frontends
	install -m 0644 ${S}/dvbsoftwareca.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/media/dvb-frontends/
	install -d ${D}/${sysconfdir}/modules-load.d
	echo "dvbsoftwareca" > ${D}/${sysconfdir}/modules-load.d/zz${MACHINE}.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/zz${MACHINE}.conf"
