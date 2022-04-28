import React, {useEffect, useState} from 'react';
import './App.css';
import FilesList from "./components/FilesList";
import {getFolderContent} from "./api/fileBrowserApi";

const App = () => {
    const [files, setFiles] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        getFolderContent("/")
            .then(response => {
                setFiles(response);
                setIsLoaded(true);
            });
    }, []);

    return (isLoaded ? <FilesList files={files}/> : <p>Loading</p>);
}

export default App;
