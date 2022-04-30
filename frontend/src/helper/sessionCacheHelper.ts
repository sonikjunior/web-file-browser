import {FileCacheProps, FileProps} from "../dto/FileProps";

export const initCache = (file: FileProps) => {
    sessionStorage.setItem('file-browser-cache', JSON.stringify(file))
}

export const addFileToCache = (file: FileProps) => {
    let cache: FileCacheProps = JSON.parse(sessionStorage.getItem('file-browser-cache')!)
    let fileProp = get(cache, file.path)
    fileProp?.content?.push(fileProp)
    sessionStorage.setItem('file-browser-cache', JSON.stringify(cache))
}

export const addFilesToCache = (path: string, files: Array<FileProps>) => {
    let cache: FileCacheProps = JSON.parse(sessionStorage.getItem('file-browser-cache')!)
    let fileProp = get(cache, path)
    if (fileProp) {
        fileProp.content = [...files]
    }
    sessionStorage.setItem('file-browser-cache', JSON.stringify(cache))
}

export const getFileFromCache = (path: string) => {
    let cache: FileCacheProps = JSON.parse(sessionStorage.getItem('file-browser-cache') || "")
    return get(cache, path)
}

const get = (fileProps: FileCacheProps, path: string): FileCacheProps | null => {
    if (fileProps.path === path) {
        return fileProps;
    }
    if (fileProps?.content) {
        for (const child of fileProps.content) {
            const found = get(child, path);

            if (found) {
                return found;
            }
        }
    }
    return null;
};
