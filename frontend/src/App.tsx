import React, {useEffect, useState} from 'react';
import './components/file/File.css';
import FilesList from "./components/fileList/FilesList";
import {getFolderContent} from "./api/fileBrowserApi";
import {addFilesToCache, getFileFromCache, initCache, isCacheEmpty} from "./helper/sessionCacheHelper";

const root = '/'

const App = () => {
    const [files, setFiles] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        if (isCacheEmpty()) {
            getFolderContent(root)
                .then(response => {
                    initCache({path: root, name: root, type: 'folder'})
                    addFilesToCache(root, response)
                    setFiles(response);
                    setIsLoaded(true);
                });
        } else {
            // @ts-ignore
            setFiles(getFileFromCache('/')?.content);
            setIsLoaded(true);
        }
    }, []);

    return (isLoaded ? <FilesList files={files}/> : <p>Loading</p>);
}

export default App;
