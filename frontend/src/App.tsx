import React, {useEffect, useState} from 'react';
import './App.css';
import FilesList from "./components/FilesList";
import {getFolderContent} from "./api/fileBrowserApi";
import {addFilesToCache, getFileFromCache, initCache, isCacheEmpty} from "./helper/sessionCacheHelper";

const App = () => {
    const [files, setFiles] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        if (isCacheEmpty()) {
            getFolderContent("/")
                .then(response => {
                    initCache({path: '/', name: '/', type: 'folder'})
                    addFilesToCache('/', response)
                    setFiles(response);
                    setIsLoaded(true);
                });
        } else {
            // @ts-ignore
            setFiles(getFileFromCache('/')?.content);
            setIsLoaded(true);
        }
    }, []);

    return (isLoaded ?
        <FilesList
            files={files}
        /> : <p>Loading</p>);

}

export default App;
