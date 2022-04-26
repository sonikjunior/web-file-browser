import {FileDto} from "../dto/FileDto";
import React, {useState} from "react";
import {getFolderContent} from "../api/fileBrowserApi";
import FilesList from "./FilesList";

const File = (file: FileDto) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [files, setFiles] = useState([]);

    const fileClickHandler = (path: string) => {
        setIsExpanded((expanded) => !expanded);
        getFolderContent(path).then(response => setFiles(response));
    }

    return (
        <li>
            <div onClick={(e) => fileClickHandler(file.path)}>
                <div>
                    {file.name}
                </div>
            </div>
            {file.type === 'folder' && isExpanded && (
                <div>
                    <ul>
                        <FilesList files={files}/>
                    </ul>
                </div>
            )}
        </li>
    );
};

export default File;
