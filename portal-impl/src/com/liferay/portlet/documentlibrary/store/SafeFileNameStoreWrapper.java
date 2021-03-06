/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;

import java.io.File;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class SafeFileNameStoreWrapper implements Store {

	public SafeFileNameStoreWrapper(Store store) {
		_store = store;
	}

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		if (!safeDirName.equals(dirName)) {
			try {
				_store.move(dirName, safeDirName);
			}
			catch (Exception e) {
			}
		}

		_store.addDirectory(companyId, repositoryId, safeDirName);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.addFile(companyId, repositoryId, safeFileName, bytes);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.addFile(companyId, repositoryId, safeFileName, file);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.addFile(companyId, repositoryId, safeFileName, is);
	}

	public void checkRoot(long companyId) throws SystemException {
		_store.checkRoot(companyId);
	}

	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.copyFileVersion(
			companyId, repositoryId, safeFileName, fromVersionLabel,
			toVersionLabel);
	}

	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		if (!safeDirName.equals(dirName)) {
			try {
				_store.deleteDirectory(companyId, repositoryId, dirName);

				return;
			}
			catch (Exception e) {
			}
		}

		_store.deleteDirectory(companyId, repositoryId, safeDirName);
	}

	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName)) {

			_store.deleteFile(companyId, repositoryId, fileName);

			return;
		}

		_store.deleteFile(companyId, repositoryId, safeFileName);
	}

	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {

			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);

			return;
		}

		_store.deleteFile(companyId, repositoryId, safeFileName, versionLabel);
	}

	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName)) {

			return _store.getFile(companyId, repositoryId, fileName);
		}

		return _store.getFile(companyId, repositoryId, safeFileName);
	}

	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {

			return _store.getFile(
				companyId, repositoryId, fileName, versionLabel);
		}

		return _store.getFile(
			companyId, repositoryId, safeFileName, versionLabel);
	}

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName)) {

			return _store.getFileAsBytes(companyId, repositoryId, fileName);
		}

		return _store.getFileAsBytes(companyId, repositoryId, safeFileName);
	}

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {

			return _store.getFileAsBytes(
				companyId, repositoryId, fileName, versionLabel);
		}

		return _store.getFileAsBytes(
			companyId, repositoryId, safeFileName, versionLabel);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName)) {

			return _store.getFileAsStream(companyId, repositoryId, fileName);
		}

		return _store.getFileAsStream(companyId, repositoryId, safeFileName);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {

			return _store.getFileAsStream(
				companyId, repositoryId, fileName, versionLabel);
		}

		return _store.getFileAsStream(
			companyId, repositoryId, safeFileName, versionLabel);
	}

	public String[] getFileNames(long companyId, long repositoryId)
		throws SystemException {

		String[] fileNames = _store.getFileNames(companyId, repositoryId);

		String[] decodedFileNames = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			decodedFileNames[i] = FileUtil.decodeSafeFileName(fileNames[i]);
		}

		return decodedFileNames;
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		if (!safeDirName.equals(dirName)) {
			try {
				_store.move(dirName, safeDirName);
			}
			catch (Exception e) {
			}
		}

		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, safeDirName);

		String[] decodedFileNames = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			decodedFileNames[i] = FileUtil.decodeSafeFileName(fileNames[i]);
		}

		return decodedFileNames;
	}

	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName)) {

			return _store.getFileSize(companyId, repositoryId, fileName);
		}

		return _store.getFileSize(companyId, repositoryId, safeFileName);
	}

	public boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		return _store.hasDirectory(companyId, repositoryId, safeDirName);
	}

	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName)) {

			return true;
		}

		return _store.hasFile(companyId, repositoryId, safeFileName);
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {

			return true;
		}

		return _store.hasFile(
			companyId, repositoryId, safeFileName, versionLabel);
	}

	public void move(String srcDir, String destDir) throws SystemException {
		_store.move(srcDir, destDir);
	}

	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.updateFile(
			companyId, repositoryId, newRepositoryId, safeFileName);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);
		String safeNewFileName = FileUtil.encodeSafeFileName(newFileName);

		if (!safeFileName.equals(fileName)) {
			if (_store.hasFile(
					companyId, repositoryId, fileName,
					DLFileEntryConstants.VERSION_DEFAULT)) {

				safeFileName = fileName;
			}
		}

		_store.updateFile(
			companyId, repositoryId, safeFileName, safeNewFileName);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.updateFile(
			companyId, repositoryId, safeFileName, versionLabel, bytes);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.updateFile(
			companyId, repositoryId, safeFileName, versionLabel, file);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.updateFile(
			companyId, repositoryId, safeFileName, versionLabel, is);
	}

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.updateFileVersion(
			companyId, repositoryId, safeFileName, fromVersionLabel,
			toVersionLabel);
	}

	protected void renameUnsafeFile(
			long companyId, long repositoryId, String fileName,
			String safeFileName)
		throws PortalException, SystemException {

		if (!safeFileName.equals(fileName)) {
			if (_store.hasFile(
					companyId, repositoryId, fileName,
					DLFileEntryConstants.VERSION_DEFAULT)) {

				_store.updateFile(
					companyId, repositoryId, fileName, safeFileName);
			}
		}
	}

	private Store _store;

}