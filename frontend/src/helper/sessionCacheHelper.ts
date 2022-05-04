import {FileCacheProps, FileProps} from "../dto/FileProps";

export const initCache = (file: FileProps) => {
    setCache(file);
}

export const isCacheEmpty = () => {
    return getCache() === null;
}

export const addFileToParentCache = (file: FileProps) => {
    const cache = getCache();
    const parentPath = getParentPath(file);
    const parentFile = getFileByPathInCache(cache, parentPath);
    if (parentFile) {
        parentFile.content?.push(file);
    }
    setCache(cache);
}

export const addFilesToCache = (path: string, files: Array<FileProps>) => {
    const cache = getCache();
    const fileProp = getFileByPathInCache(cache, path);
    if (fileProp) {
        fileProp.content = [...files];
    }
    setCache(cache);
}

export const getFileFromCache = (path: string) => {
    const cache = getCache();
    return getFileByPathInCache(cache, path);
}

export const deleteFileFromCache = (file: FileProps) => {
    const cache = getCache();
    const parentPath = getParentPath(file)
    const parentFile = getFileByPathInCache(cache, parentPath);
    if (parentFile) {
        const fileContentToDelete = parentFile.content?.find(a => a.path === file.path);
        if (fileContentToDelete) {
            fileContentToDelete.content = null;
        }
    }
    setCache(cache);
}


const fileBrowserCache = 'file-browser-cache';

const getCache = () => {
    return JSON.parse(sessionStorage.getItem(fileBrowserCache)!)
}

const setCache = (cache: FileCacheProps) => {
    sessionStorage.setItem(fileBrowserCache, JSON.stringify(cache))
}

const getParentPath = (file: FileProps) => {
    let parentPath = file.path.substring(0, file.path.length - file.name.length - 1);
    if (parentPath.length === 0) {
        parentPath = '/'
    }
    return parentPath;
}

const getFileByPathInCache = (fileProps: FileCacheProps, path: string): FileCacheProps | null => {
    if (fileProps.path === path) {
        return fileProps;
    }
    if (fileProps?.content) {
        for (const child of fileProps.content) {
            const found = getFileByPathInCache(child, path);
            if (found) {
                return found;
            }
        }
    }
    return null;
};
