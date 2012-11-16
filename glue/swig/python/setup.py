#!/usr/bin/env python

from distutils.core import setup

# Project metadata

name='swagger'
version='0.0.1'
description='Python bindings for libalexandria'
long_description="""A fast and easy-to-use library for machine learning."""

author='Tor E Hagemann'
author_email='hagemt@rpi.edu'
url='http://hagemt.github.com/libalexandria/'

# Package metadata

install_requires = [ 'setuptools' ]
keywords='libalexandria machine learning'
license='LGPL'
packages=[ 'swagger' ]

classifiers=[
 "Development Status :: 2 - Pre-Alpha",
 "Intended Audience :: Developers",
 "Intended Audience :: Science/Research",
 "License :: OSI Approved :: GNU Library or Lesser General Public License (LGPL)",
 "Programming Language :: C",
 "Programming Language :: C++",
 "Programming Language :: Fortran",
 "Programming Language :: Java",
 "Programming Language :: Python",
 "Programming Language :: Ruby",
 "Topic :: Scientific/Engineering :: Artificial Intelligence",
 "Topic :: Scientific/Engineering :: Information Analysis",
 "Topic :: Scientific/Engineering :: Mathematics",
 "Topic :: Software Development :: Libraries",
 "Topic :: Software Development :: Libraries :: Java Libraries",
 "Topic :: Software Development :: Libraries :: Python Modules",
 "Topic :: Software Development :: Libraries :: Ruby Modules",
]

# TODO run setup with project parameters:
#setup(...)
