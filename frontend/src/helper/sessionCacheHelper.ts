import {FileCacheProps, FileProps} from "../dto/FileProps";

export const initCache = (file: FileProps) => {
    sessionStorage.setItem('file-browser-cache', JSON.stringify(file))
}

export const isCacheEmpty = () => {
    return sessionStorage.getItem('file-browser-cache') === null
}

export const addFileToParentToCache = (file: FileProps) => {
    const parentPath = file.path.substring(0, file.path.length - file.name.length - 1)
    let cache: FileCacheProps = JSON.parse(sessionStorage.getItem('file-browser-cache')!)
    let fileProp = getFileByPathInCache(cache, parentPath)
    if (fileProp) {
        fileProp.content?.push(file)
    }
    sessionStorage.setItem('file-browser-cache', JSON.stringify(cache))
}

export const addFilesToCache = (path: string, files: Array<FileProps>) => {
    let cache: FileCacheProps = JSON.parse(sessionStorage.getItem('file-browser-cache')!)
    let fileProp = getFileByPathInCache(cache, path)
    if (fileProp) {
        fileProp.content = [...files]
    }
    sessionStorage.setItem('file-browser-cache', JSON.stringify(cache))
}

export const getFileFromCache = (path: string) => {
    let cache: FileCacheProps = JSON.parse(sessionStorage.getItem('file-browser-cache') || "")
    return getFileByPathInCache(cache, path)
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
