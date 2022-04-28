export interface Props {
    files: FileDto[]
}

export interface ChildProps {
    file: FileDto
    setListState: Function
}

export interface FileDto {
    path: string
    name: string
    type: string
}
