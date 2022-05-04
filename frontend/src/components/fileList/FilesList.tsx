import React, {useState} from 'react';
import './FileList.css';
import {FileProps, Props} from '../../dto/FileProps';
import File from "../file/File";

const FilesList = (props: Props) => {
    const [files, setFiles] = useState(props.files);

    const updateState = (newFile: FileProps) => {
        setFiles([...files, newFile])
    }

    return (
        <div>
            <ul className="files-list">
                {files.map((file: FileProps) =>
                    (<File
                        key={file.path}
                        file={file}
                        updateParentState={updateState}
                    />)
                )}
            </ul>
        </div>
    );
};

export default FilesList;
