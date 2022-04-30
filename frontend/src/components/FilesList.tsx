import React, {useState} from 'react';
import '../App.css';
import {FileProps, Props} from '../dto/FileProps';
import File from "./File";

const FilesList = (props: Props) => {
    const [files, setFiles] = useState(props.files);

    const setListState = (newFile: FileProps) => {
        setFiles([...files, newFile])
    }

    return (
            <div>
                <ul className="files-list">
                    {files.map((file: FileProps) =>
                        (<File
                            key={file.path}
                            file={file}
                            setListState={setListState}
                        />)
                    )}
                </ul>
            </div>
    );
};

export default FilesList;
