import React, {useEffect, useState} from "react";
import '../App.css';
import {ChildProps, FileProps} from "../dto/FileProps";
import {addFilesToCache, addFileToParentToCache} from "../helper/sessionCacheHelper";
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

    let {file, updateParentState} = childProps

    useEffect(() => {
        // check the cache
        if (childProps.file.content) {
            const children = file.content
            // @ts-ignore
            setChildFiles(children);
            setIsLoaded(true)
            setIsExpanded((expanded) => !expanded)
        }
    }, [])

    const fileClickHandler = (file: FileProps) => {
        if (file.type === 'archive') {
            unzipArchive(file.path)
                .then(unzippedFolder => {
                    addFileToParentToCache(unzippedFolder);
                    updateParentState(unzippedFolder);
                });
        }
        if (file.type === 'folder') {
            getFolderContent(file.path)
                .then(folderContent => {
                    addFilesToCache(file.path, folderContent);
                    setChildFiles(folderContent);
                    setIsLoaded(true);
                });
            setIsExpanded((expanded) => !expanded);
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
