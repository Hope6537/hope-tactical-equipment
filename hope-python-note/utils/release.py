# encoding: utf-8

"""Release information about Marrow IO."""

from collections import namedtuple

__all__ = ['version_info', 'version']

version_info = namedtuple('version_info', ('major', 'minor', 'micro', 'releaselevel', 'serial'))(1, 1, 0, 'final', 0)

version = ".".join([str(i) for i in version_info[:3]]) + (
(version_info.releaselevel[0] + str(version_info.serial)) if version_info.releaselevel != 'final' else '')

name = "marrow.io"
version = "1.1.0"
release = "1.1"

summary = "Standalone versions of the Tornado IOLoop and IOStream."
description = """"""
author = "Facebook"
email = ""
url = "http://github.com/pulp/marrow.io"
download_url = "http://github.com/pulp/marrow.io/downloads"
copyright = "2009, Facebook"
license = "Apache 2.0"
