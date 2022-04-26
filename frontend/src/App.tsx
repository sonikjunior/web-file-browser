import React, {useEffect, useState} from 'react';
import './App.css';
import FilesList from "./components/FilesList";
import {getFolderContent} from "./api/fileBrowserApi";

const App = () => {
    const [files, setFiles] = useState([]);

    useEffect(() => {
        getFolderContent("/")
            .then(response => setFiles(response));
    })

    return <FilesList files={files}/>
}

export default App;
