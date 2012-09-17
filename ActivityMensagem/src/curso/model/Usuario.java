package curso.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

	private Long user_id;
	private String user_nome;
	private String user_senha;

	public String getUser_senha() {
		return user_senha;
	}

	public void setUser_senha(String user_senha) {
		this.user_senha = user_senha;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_nome() {
		return user_nome;
	}

	public void setUser_nome(String user_nome) {
		this.user_nome = user_nome;
	}

	public Usuario(Parcel in) {
		this.user_id = in.readLong();
		this.user_nome = in.readString();
		this.user_senha = in.readString();
	}

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.getUser_id());
		dest.writeString(this.getUser_nome());
		dest.writeString(this.getUser_senha());
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Usuario createFromParcel(Parcel in) {
			return new Usuario(in);
		}

		public Usuario[] newArray(int size) {
			return new Usuario[size];
		}
	};

	public Usuario() {
	};

}
