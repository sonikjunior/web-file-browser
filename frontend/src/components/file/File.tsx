import React, {useEffect, useState} from "react";
import './File.css';
import {ChildProps, FileProps} from "../../dto/FileProps";
import {addFilesToCache, addFileToParentCache, deleteFileFromCache} from "../../helper/sessionCacheHelper";
import FilesList from "../fileList/FilesList";
import {getFolderContent, unzipArchive} from "../../api/fileBrowserApi";
import {resolveType} from "../../helper/imageHelper";
import arrow from "../../img/angle-right-solid.svg";


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
                    addFileToParentCache(unzippedFolder);
                    updateParentState(unzippedFolder);
                });
        }
        if (file.type === 'folder') {
            if (!isExpanded) {
                getFolderContent(file.path)
                    .then(folderContent => {
                        addFilesToCache(file.path, folderContent);
                        setChildFiles(folderContent);
                        setIsLoaded(true);
                    });
            } else {
                deleteFileFromCache(file)
            }
            setIsExpanded(!isExpanded);
        }
    }

    return (
        <li>
            <div onClick={() => fileClickHandler(file)}>
                <div>
                    {file.type === 'folder' &&
                        <img src={arrow} alt="arrow-right"
                             className={`arrow ${isExpanded ? "active" : ""}`}/>}

                    <img src={resolveType(file.type)} alt="arrow-right"
                         className={`image ${file.type !== 'folder' ? "not-folder" : ""}`}/>
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
