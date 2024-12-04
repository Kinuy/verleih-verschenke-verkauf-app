import {Item} from "../models/Item.ts";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import axios from "axios";
import "./NewItemCard.css";
import {ItemCategory} from "../models/ItemCategory.ts";
import {ItemStatus} from "../models/ItemStatus.ts";
import {ItemDto} from "../models/ItemDto.ts";

type Props = {
    updateList: () => void;
    items:Item[];
}

export default function NewItemCard(props: Props) {

    const [itemName, setItemName] = useState("");
    const [itemImg, setItemImg] = useState("");
    const [itemDescription, setItemDescription] = useState("");
    const [itemCategory, setItemCategory] = useState<ItemCategory>();
    const [itemStatus, setItemStatus] = useState<ItemStatus>();
    const [id, setId] = useState<string>("")
    const [image, setImage] = useState<File | null>(null)

    function manageItem(event: FormEvent<HTMLFormElement>){
        event.preventDefault()
        if(id===""){
            addItem();
        }
        else{
            updateItemDetails(id)
        }
    }

    function addItem() {
        if (!itemCategory || !itemStatus) {
            alert("Please check your input: category or status!");
            return;
        }

        const data: FormData = new FormData();

        if (image) {
            data.append("image", image);
        }

        const savedItem: ItemDto = {
            name: itemName || 'Default Name',
            img: itemImg || 'default/url',
            description: itemDescription || 'Default description',
            category: itemCategory || 'TOOL',
            status: itemStatus || 'TO_SELL'
        };

        data.append("itemDto", new Blob([JSON.stringify(savedItem)], {'type': "application/json"}))

        axios
            .post(`/api/item`, data, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            })
            .then(() => {props.updateList()})
            .catch((error) => {
                console.error("Error saving image:", error);
            });

    }

    function deleteItem() {
        const checkDelete = window.confirm("Do you really want to delete this item?")
        if (checkDelete) {
            axios.delete(`api/item/${id}`)
                .then(()=>{
                    setId("");
                    props.updateList();
                    }
                )
                .catch((error) => {
                    console.error(error)
                })

        }
    }

    function fetchItemDetails(id:string) {

        axios.get(`/api/item/${id}`)
            .then((response) => {
                setItemName(response.data.name);
                setItemImg(response.data.img);
                setItemDescription(response.data.description);
                setItemCategory(response.data.category);
                setItemStatus(response.data.status);
            })
            .catch(error => console.error(error));

        setId(id);

    }

    function updateItemDetails(id:string){

        const data: FormData = new FormData();

        if (image) {
            data.append("image", image);

        }

        fetchItemDetails(id);

        const itemData = {
            id:id,
            name: itemName,
            image: itemImg,
            description: itemDescription,
            category: itemCategory,
            status: itemStatus,
        };
        data.append("itemDTO", new Blob([JSON.stringify(itemData)], {'type': "application/json"}))

        axios
            .put(`/api/item/${id}`, data, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            })
            .then(()=>{props.updateList()})
            .catch((error) => {
                console.error("Error saving image:", error);
            });

    }

    function onImageFileChange(e: ChangeEvent<HTMLInputElement>) {
        e.preventDefault();
        if (e.target.files) {
            setImage(e.target.files[0]);
        }
    }
    useEffect(() => {
        props.updateList();
    }, []);

    return (
        <div className="manage-container">

            <form className="newItemForm" onSubmit={manageItem}>
                <h2>Modify Item</h2>
                <div className="update-item-container">
                    <select
                        className="select-item-container"
                        name="item"
                        id="item-select"
                        onChange={(e) => {
                            const id: string = e.target.value;
                            setId(id);
                            fetchItemDetails(id);
                        }}
                    >
                        <option value="">--Select item to update--</option>
                        {
                            props.items.map((element) => (
                                <option key={element.id} value={element.id}>
                                    {element.name}
                                </option>
                            ))
                        }
                    </select>
                </div>
                <label>
                    <p>Item name:</p>
                    <input
                        type="text"
                        value={itemName}
                        placeholder={itemName}
                        onChange={(e) => setItemName(e.target.value)}
                    />
                </label>
                <label>
                    <div className="image-dialogue-container">
                        {image && <img src={URL.createObjectURL(image)} alt={"image not found"}/>}
                        <input style={{display:"none"}}
                               id={"file-input"}
                            type={"file"}
                            onChange={onImageFileChange}
                        />
                        <div className="load-img">
                            {image ? <label className="load-img-message">{image.name}</label> :
                                <label className="load-img-message">no image loaded</label>}
                            <label className="load-img-button" htmlFor={"file-input"}>
                                load image
                            </label>
                        </div>
                    </div>
                </label>
                <label>
                    <p>Description:</p>
                    <textarea
                        value={itemDescription}
                        placeholder="description"
                        onChange={(e) => setItemDescription(e.target.value)}
                    />
                </label>
                <label>
                    <p>Category:</p>
                    <select value={itemCategory} name="category" id="category-select"
                            onChange={(e) => setItemCategory(e.target.value as ItemCategory)}>
                        <option value={undefined}>--Choose category--</option>
                        <option value="TOOL">TOOL</option>
                        <option value="MATERIAL">MATERIAL</option>
                    </select>
                </label>
                <label>
                    <p>Status:</p>
                    <select value={itemStatus} className="select-status-container" name="status" id="status-select"
                            onChange={(e) => setItemStatus(e.target.value as ItemStatus)}>
                        <option value={undefined}>--Choose status--</option>
                        <option className="lend-container" value="TO_LEND">TO_LEND</option>
                        <option className="give-container" value="TO_GIVE_AWAY">TO_GIVE_AWAY</option>
                        <option className="sell-container" value="TO_SELL">TO_SELL</option>
                    </select>
                </label>
                {
                    (id === "") && <button className="button-item-container">add</button>
                }
                {
                    (id !== "") && (<button className="button-item-container">update</button>)
                }
            </form>
            {
                (id !== "") && (<button className="button-item-container" onClick={deleteItem}>delete</button>)
            }
        </div>
    );
}
;