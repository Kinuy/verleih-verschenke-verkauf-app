import {ChangeEvent, useState} from "react";
import './ItemSearchBar.css';

type SearchBarProps = {
    onSearch: (query: string) => void;
};

const SearchBar = ({ onSearch }: SearchBarProps) => {
    const [query, setQuery] = useState('');

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        setQuery(value);

        const queryToSearch =value.toLowerCase();
        onSearch(queryToSearch);
    };

    return (
        <div className="search-bar">
            <input
                type="text"
                placeholder="Items..."
                value={query}
                onChange={handleChange}
                className="search-input"
            />
        </div>
    );
};

export default SearchBar