import React, {useState} from "react";
import '../App.css';
import {ChildProps, FileDto} from "../dto/FileDto";
import FilesList from "./FilesList";
import {getFolderContent, unzipArchive} from "../api/fileBrowserApi";
import doc from "../img/file-lines-regular.svg";
import file from "../img/file-regular.svg";
import folder from "../img/folder-regular.svg";
import picture from "../img/image-regular.svg";
import archive from "../img/file-zipper-regular.svg";


const resolveType = (type: string) => {
    switch (type) {
        case 'folder': return folder
        case 'archive': return archive
        case 'document': return doc
        case 'picture': return picture
        default: return file
    }
}

const File = (childProps: ChildProps) => {
    const [isLoaded, setIsLoaded] = useState(false);
    const [isExpanded, setIsExpanded] = useState(false);
    const [childFiles, setChildFiles] = useState([]);

    let {file, setListState} = childProps

    const fileClickHandler = (file: FileDto) => {
        if (file.type === 'archive') {
            unzipArchive(file.path)
                .then(unzippedFolder => setListState(unzippedFolder));
        }
        if (file.type === 'folder') {
            setIsExpanded((expanded) => !expanded);
            getFolderContent(file.path).then(folderContent => {
                setChildFiles(folderContent);
                setIsLoaded(true)
            });
        }
    }

    return (
        <li>
            <div onClick={() => fileClickHandler(file)}>
                <div>
                    <img src={resolveType(file.type)} alt={""} className="image"/>
                    {file.name}
                </div>
            </div>
            {file.type === 'folder' && isExpanded && isLoaded && (
                <div>
                    <ul>
                        <FilesList files={childFiles}/>
                    </ul>
                </div>
            )}
        </li>
    );
};

export default File;
