import React, {useEffect, useState} from 'react';
import './App.css';
import FilesList from "./components/FilesList";
import {getFolderContent} from "./api/fileBrowserApi";
import {addFilesToCache, initCache} from "./helper/sessionCacheHelper";

const App = () => {
    const [files, setFiles] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        getFolderContent("/")
            .then(response => {
                initCache({path: '/', name: '/', type: 'folder'})
                addFilesToCache('/', response)
                setFiles(response);
                setIsLoaded(true);
            });
    }, []);

    return (isLoaded ? <FilesList files={files}/> : <p>Loading</p>);
}

export default App;
