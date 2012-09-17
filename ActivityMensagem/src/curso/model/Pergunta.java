package curso.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pergunta implements Parcelable {

	private Long ask_id;
	private Long user_id;
	private String ask_pergunta;
	private String user_name;
	private boolean status;

	public Long getAsk_id() {
		return ask_id;
	}

	public void setAsk_id(Long ask_id) {
		this.ask_id = ask_id;
	}

	public String getAsk_pergunta() {
		return ask_pergunta;
	}

	public void setAsk_pergunta(String ask_pergunta) {
		this.ask_pergunta = ask_pergunta;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Pergunta(Parcel in) {
		this.ask_id = in.readLong();
		this.user_id = in.readLong();
		this.ask_pergunta = in.readString();
	}

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.getAsk_id());
		dest.writeLong(this.getUser_id());
		dest.writeString(this.getAsk_pergunta());
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Pergunta createFromParcel(Parcel in) {
			return new Pergunta(in);
		}

		public Pergunta[] newArray(int size) {
			return new Pergunta[size];
		}
	};

	public Pergunta() {
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	};

}
